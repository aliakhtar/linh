package actor.ali.actor

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}

import actor.ali.linh.config.Env
import actor.ali.linh.input.{AddToStock, ChangePrice, Command, NewOrder}
import actor.ali.linh.util.Json
import actor.ali.linh.response.{Output, Suggestions}
import actor.ali.linh.response.OutputItem.{logg, success, text}
import actor.ali.linh.store.{Item, Store}
import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.google.common.cache.{Cache, CacheBuilder, CacheLoader, LoadingCache}
import play.api.{Environment, Logger, Mode, Play}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Random, Success, Try}
import scala.collection.mutable
import scala.concurrent.duration._

class WebsocketActor(env: Env, e: Environment, out: ActorRef) extends Actor {

    private val log = Logger("WebsocketActor")

    //private implicit val ec: ExecutionContext = env.ioEc

    private val store = new Store()

    log.info(Class.forName("actor.ali.linh.input.Commands").descriptorString())

    def withClassLoaderHack[E](f: () => E): E = {
        e.mode match {
            case Mode.Dev =>
                val old = Thread.currentThread().getContextClassLoader
                try {
                    Thread.currentThread().setContextClassLoader(e.classLoader)
                    f()
                } finally {
                    Thread.currentThread().setContextClassLoader(old)
                }
            case _ =>
                f()
        }
    }

    welcome()

    private def welcome():Unit = {
        val suggestions: Seq[String] = Seq(1, 2, 3).map(_ => s"add ${randNumber()} ${randItem()}")

        val content = Seq(WebsocketActor.welcome1, WebsocketActor.welcome2, WebsocketActor.welcome3)
        val output = Output(content, Some(Suggestions("Suggestions:", suggestions)))

        out ! Json.render(output)
    }

    private def randItem():String = {
        val i = WebsocketActor.random.nextInt( WebsocketActor.itemNames.length )
        WebsocketActor.itemNames(i)
    }


    private def randNumber():Int = {
        val i = WebsocketActor.random.nextInt( WebsocketActor.roundNumbers.length )
        WebsocketActor.roundNumbers(i)
    }


    def processCmd(result: Command): Unit = {
        result match {
            case AddToStock(name, qty) =>
                if ( store.exists(name)) {
                    sendLog(s"You already have a product called <strong>${name}. Please choose another name.")
                    return
                }

                val item = Item(name, randNumber(), qty)
                this.store.add(item)
                val suggestions = (0 to 3).map(_ => randNumber()).filterNot(_ == item.price)
                    .map(price => "set " + name + " price to $" + price)
                sendNormal("Added <strong> " + qty + "</strong> " + name + "</strong> - Price: $" + item.price, suggestions)


            case ChangePrice(name, newPrice) =>
                if (! validateItem(name))
                    return

                store.updatePrice(name, newPrice)
                val suggestions = (0 to 3).map(_ => s"sell ${randNumber()} $name")
                sendNormal("Price Updated", suggestions)


            case NewOrder(name, qty) =>
                if (! validateItem(name))
                    return

                if (! store.canOrder(name, qty)) {
                    sendNormal(s"You don't have $qty items of $name!", Seq("best seller", s"total revenue of $name",
                    s"total orders of $name"))
                    return
                }

                store.newOrder(NewOrder(name, qty))
                sendNormal(s"Order Saved.", Seq("total revenue", s"total revenue of $name",
                    s"total orders of $name", ))
        }

    }

    private def validateItem(name: String):Boolean = {
        if (! store.exists(name)) {
            sendLog(s"You haven't added a product called <strong>${name}</strong> yet. Would you like to add it?",
                Suggestions("Suggestions", Seq(s"add ${randNumber()} $name")))
            return false
        }

        return true
    }

    private def parse(cmd: String):Unit = {
        Try(withClassLoaderHack(() => env.client.parse(cmd))) match {
            case Success(Some(result: Command)) => processCmd(result)
            case Success(None) =>
                sendLog()

            case Failure(e) =>
                e.printStackTrace()

        }
    }


    private def sendSuccess(msg: String, suggestions: Seq[String] = Nil ):Unit = {
        if (suggestions.isEmpty)
            out ! Json.render(Output.single(success(msg)))
        else
            out ! Json.render(Output.single(success(msg), Suggestions("Suggestions", suggestions)))
    }

    private def sendNormal(msg: String, suggestions: Seq[String] = Nil ):Unit = {
        if (suggestions.isEmpty)
            out ! Json.render(Output.single(text(msg)))
        else
            out ! Json.render(Output.single(text(msg), Suggestions("Suggestions", suggestions)))
    }

    private def sendLog(msg: String = "Sorry, I don't quite understand that."):Unit = {
        out ! Json.render(Output.single(logg(msg)))
    }

    private def sendLog(msg: String, suggestions: Suggestions):Unit = {
        out ! Json.render(Output.single(logg(msg), suggestions))
    }

    override def receive: Receive = {

        case "ping" =>
                out! "pong"

        case msg: String =>
            log.info(s"Received msg: $msg")
            parse(msg)
    }
}


object WebsocketActor {

    private val format = new SimpleDateFormat("yyyy-MM-dd")

    private val welcome1 = text(s"Xin Chào (Hi)! I'm Linh, a store management AI.")
    private val welcome2 = text(s"I can help you manage your store, and answer all kinds of questions about your data.")
    private val welcome3 = text(s"Lets start by adding some products to your store.")

    private val random = new Random

    private val itemNames = Seq("oranges", "apples", "eggs", "milk" )
    private val roundNumbers = Seq(1,5,10,25,50,100)

    private def today():String = format.format(new Date())

    def props(env: Env, e: Environment, out: ActorRef): Props =
        Props(new WebsocketActor(env, e, out))
}
