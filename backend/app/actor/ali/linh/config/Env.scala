package actor.ali.linh.config

import actor.ali.linh.sempre.SempreClient
import actor.ali.linh.util.{IO, Json}

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

class Env {
    private val log = Logger[Env]
    log.info("Starting env");

    val conf: Config = Json.parse[Config](IO.readResource("config.json").mkString)

    val client = new SempreClient(this)
}

object Env {

    val instance = new Env
}
