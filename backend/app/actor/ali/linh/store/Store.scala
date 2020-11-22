package actor.ali.linh.store

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

class Store {

    private val log = Logger[Store]


    private val items: collection.mutable.Map[String, Item] =
        collection.mutable.Map.empty


    def exists(name: String): Boolean = items.contains(name)

    def add(item: Item): Unit = items.addOne( (item.name), item )

    def updatePrice(name :String, price:Double): Unit = {
        val newI = items(name).copy(price = price)
        items(name) = newI

    }
}
