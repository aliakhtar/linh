package actor.ali.linh.sempre

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


class PreprocessorTest extends AnyFunSuite with Matchers {

    private val log = Logger[PreprocessorTest]

    test("testProcess") {
        Preprocessor.process("display inv") should be("show inventory")
        Preprocessor.process("show inventory") should be("show inventory")
    }

}
