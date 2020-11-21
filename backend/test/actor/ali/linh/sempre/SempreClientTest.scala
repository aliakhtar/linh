package actor.ali.linh.sempre

import actor.ali.linh.config.Env
import actor.ali.linh.input.{Command, ShowInventory}
import com.typesafe.scalalogging.Logger
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest._
import matchers.should._
import matchers.should.Matchers._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{Await, Future}
import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}


class SempreClientTest extends AnyFunSuite with Matchers {

    private val log = Logger[SempreClientTest]

    private val client = new SempreClient(Env.instance)

    test("stock") {
        check(ShowInventory(), "show", "inventory")
        checkRaw(ShowInventory(), "show inventories")
    }


    //noinspection SameParameterValue
    private def check(expected: Command, words: String*):Unit = {
        val cmd = words.mkString(" ")
        val variations = words.flatMap(w => {
            Preprocessor.getSyns(w).map(syn => cmd.replace(w, syn))
        })

        variations.foreach(i => checkRaw(expected, i))
    }

    private def checkRaw(expected: Command, input: String):Unit = {
        log.info(s"Checking ${input} against $expected");
        client.parse(input) should be(Some(expected))
    }

}
