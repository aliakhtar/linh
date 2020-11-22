package actor.ali.actor

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}

import actor.ali.linh.config.Env
import actor.ali.linh.util.Json
import actor.ali.response.Output
import actor.ali.response.OutputItem.{logg, text}
import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.google.common.cache.{Cache, CacheBuilder, CacheLoader, LoadingCache}
import play.api.Logger

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import scala.collection.mutable
import scala.concurrent.duration._

class WebsocketActor(env: Env, out: ActorRef) extends Actor {

    private val log = Logger("WebsocketActor")

    //private implicit val ec: ExecutionContext = env.ioEc

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

    private val version = "1.0.3"

    private val welcome1 = text(s"Linh, v$version.")
    private val welcome2 = text(s"This system answers (most) questions about Ali Akhtar.")

    private val suggestions = Seq("who are you")

    private def today():String = format.format(new Date())

    def props(env: Env, out: ActorRef): Props =
        Props(new WebsocketActor(env, out))
}
