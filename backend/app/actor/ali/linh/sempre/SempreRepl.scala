package actor.ali.linh.sempre

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

import scala.io.StdIn

object SempreRepl extends App {

    private val log = Logger("REPL")

    private val client = new SempreClient


    while (true) {
        val q = StdIn.readLine("Enter text:")
        log.info("Parsing...");
        val r: SempreResponse = client.parse(q)
        log.info(s"Response: ${r}");
    }
}
