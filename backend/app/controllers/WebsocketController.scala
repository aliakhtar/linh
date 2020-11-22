package controllers

import actor.ali.actor.WebsocketActor
import actor.ali.linh.config.Env
import akka.actor.ActorSystem
import akka.stream.Materializer
import controllers.util.DaddyController
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.streams.ActorFlow
import play.api.mvc.{ControllerComponents, _}

import scala.concurrent.ExecutionContext


@Singleton
class WebsocketController @Inject()(cc : ControllerComponents)
                                   (implicit system: ActorSystem, mat: Materializer, ec: ExecutionContext)
  extends DaddyController(cc) {


    private val log = Logger("WebsocketController")
    log.info("Starting WebsocketController");

    private val env = Env.instance

    def index(): WebSocket = WebSocket.accept[String, String] { request =>

        log.info("in websocketcontroller index")
        ActorFlow.actorRef { out =>
            WebsocketActor.props(env, out)
        }
    }
}