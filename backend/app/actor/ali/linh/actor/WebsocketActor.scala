package actor.ali.actor

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}

import actor.ali.linh.config.Env
import actor.ali.linh.util.Json
import actor.ali.linh.response.{Output, Suggestions}
import actor.ali.linh.response.OutputItem.{logg, text}
import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.google.common.cache.{Cache, CacheBuilder, CacheLoader, LoadingCache}
import play.api.Logger

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Random, Success, Try}
import scala.collection.mutable
import scala.concurrent.duration._

class WebsocketActor(env: Env, out: ActorRef) extends Actor {

    private val log = Logger("WebsocketActor")

    //private implicit val ec: ExecutionContext = env.ioEc



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



    private def processCommand(cmd: String):Unit = {

    }

    private def respondWithSuggestions(cmd: String):Unit = {

    }

    private def unknownInput(msg: String):Unit = {
        out ! Json.render(Output.single(logg("No matches found.")))
    }

    override def receive: Receive = {

        case "ping" =>
                out! "pong"

        case msg: String =>
            log.info(s"Received msg: $msg")
            processCommand(msg)
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

    def props(env: Env, out: ActorRef): Props =
        Props(new WebsocketActor(env, out))
}
