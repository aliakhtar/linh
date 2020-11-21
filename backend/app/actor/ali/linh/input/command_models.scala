package actor.ali.linh.input

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

sealed trait Command
case class ItemRef(name: String) extends Command {
    override def toString: String = name
}

case class ShowInventoryAllStore() extends Command
case class ShowInventoryItem(item: String) extends Command

case class AddToStock(name: String, qty: Double) extends Command
case class ChangePrice(name: String, newPrice: Double) extends Command

case class NewOrder(name: String, qty: Double) extends Command

case class HowMuchStoreRevenue() extends Command
case class HowMuchItemRevenue(name: String) extends Command

case class HowManyStoreOrders() extends Command
case class HowManyItemOrders(name: String) extends Command

case class BestSellers() extends Command
case class WorstSellers() extends Command

case class WhatShouldIBuy() extends Command
case class HowMuchToBuy(name: String) extends Command
