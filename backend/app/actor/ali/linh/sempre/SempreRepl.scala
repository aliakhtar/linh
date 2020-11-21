package actor.ali.linh.sempre

import actor.ali.linh.config.Env

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

import scala.io.StdIn

object SempreRepl extends App {

    private val log = Logger("REPL")

    private val env = Env.instance
    private val client = new SempreClient(env)


    while (true) {
        val q = StdIn.readLine("Enter text:")
        log.info("Parsing...");
        val r: SempreResponse = client.parse(q)
        Try {
            log.info(s"Derivation: ${r.getDerivation}");
            log.info(s"Answer: ${r.getAnswer}");
        } match {
            case Success(_) => // Do nothing
            case Failure(e) => log.warn(s"Could not parse: ${e.getMessage}")
        }
    }
}
