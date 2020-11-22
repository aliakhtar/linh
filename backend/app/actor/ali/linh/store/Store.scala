package actor.ali.linh.store

import actor.ali.linh.input.NewOrder

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

class Store {

    private val log = Logger[Store]


    private val items: collection.mutable.Map[String, Item] =
        collection.mutable.Map.empty


    private val orders = collection.mutable.Buffer.empty[NewOrder]


    def exists(name: String): Boolean = items.contains(name)

    def add(item: Item): Unit = items.addOne( (item.name), item )

    def updatePrice(name :String, price:Double): Unit = {
        val newI = items(name).copy(price = price)
        items(name) = newI
    }

    def canOrder(name: String, qty: Double):Boolean = items(name).qty >= qty

    def newOrder(o: NewOrder): orders.type = {
        val i = items(o.name)
        val newI = i.copy(orders = i.orders + 1, qty = i.qty - 1)
        items(o.name) = newI

        this.orders.addOne(o)
    }

    def totalRev():Double = {
        this.orders.map(o => items(o.name).price * o.qty).sum
    }

    def totalRev(item: String):Double = {
        this.orders.filter(_.name == item).map(o => items(o.name).price * o.qty).sum
    }

    def totalOrders():Double = this.orders.size

    def totalOrders(name: String):Double = this.orders.count(_.name == name)

    def bestSeller():Option[Item] = this.items.values.toSeq.sortBy(- _.orders).headOption

    def worstSeller():Option[Item] = this.items.values.toSeq.sortBy(_.orders).headOption

    def whatToBuy():Option[Item] = this.items.values.toSeq.sortBy(_.qty).headOption
}

