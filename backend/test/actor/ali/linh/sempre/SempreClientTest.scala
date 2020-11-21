package actor.ali.linh.sempre

import actor.ali.linh.config.Env
import actor.ali.linh.input.{AddToStock, ChangePrice, Command, HowManyItemOrders, HowManyStoreOrders, HowMuchItemRevenue, HowMuchStoreRevenue, ItemRef, NewOrder, ShowInventoryAllStore, ShowInventoryItem}
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
        check(ShowInventoryAllStore(), "show", "inventory")
        checkRaw(ShowInventoryAllStore(), "show inventories")
        checkRaw(ShowInventoryAllStore(), "show me inventory")
        checkRaw(ShowInventoryAllStore(), "show me the inventory")
        checkRaw(ShowInventoryAllStore(), "show the inventory")


        checkRaw(ShowInventoryItem("apples"), "show inventory of apples")
        checkRaw(ShowInventoryItem("apples"), "show inventory of the apples")
        checkRaw(ShowInventoryItem("balls"), "show inventory of the balls")

        check(ShowInventoryItem("apples"), "how",  "many", "apples", "left")
        check(ShowInventoryItem("apples"), "how",  "many", "apples", "are", "left")
    }

    test("item ref") {
        checkRaw(ItemRef("apple"), "of apple")
        checkRaw(ItemRef("apples"), "of apples")
        checkRaw(ItemRef("apples"), "of the apples")
        checkRaw(ItemRef("apples"), "of apples")
    }

    test("add to stock") {
        check(AddToStock("apples", 10), "bought", "10", "apples")
        check(AddToStock("apples", 100), "bought", "100", "of", "apples")
    }

    test("set price") {
        check(ChangePrice("apple", 10),"set", "apple", "price", "to", "10")
        check(ChangePrice("apple", 10),"set", "apple", "price", "to", "$10")

        check(ChangePrice("apple", 20), "apple", "price", "to", "20")
        check(ChangePrice("apple", 20), "apple", "price", "to", "$20")

        check(ChangePrice("apple", 30), "apple", "price",  "30")
        check(ChangePrice("apple", 30), "apple", "price",  "$30")

        check(ChangePrice("nailpolish", 40.5), "set", "nailpolish", "price", "to", "$40.50")
    }



    test("new order") {
        check(NewOrder("apples", 10), "sold", "10", "apples")
        check(NewOrder("apples", 100), "sold", "100", "of", "apples")
    }

    test("store revenue and orders") {
        check(HowMuchStoreRevenue(), "how", "much", "revenue")
        check(HowMuchStoreRevenue(), "how", "much", "total", "revenue")

        check(HowManyStoreOrders(), "how", "many", "orders")
        check(HowManyStoreOrders(), "how", "many", "total", "orders")
    }

    test("item revenue and orders") {
        check(HowMuchItemRevenue("apples"), "how", "much", "revenue", "from", "apples")
        check(HowMuchItemRevenue("apples"), "how", "much", "total", "revenue", "from", "apples")
        check(HowMuchItemRevenue("apples"), "how", "much", "revenue", "by", "apples")
        check(HowMuchItemRevenue("apples"), "how", "much", "total", "revenue", "by", "apples")

        check(HowManyItemOrders("apples"), "how", "many", "orders", "of", "apples")
        check(HowManyItemOrders("apples"), "how", "many", "total", "orders", "of", "apples")

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
