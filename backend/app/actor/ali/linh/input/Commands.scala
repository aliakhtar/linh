package actor.ali.linh.input

import actor.ali.linh.sempre.CommandValue

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

object Commands {

    private val log = Logger("Commands")

    private def wrap(c: Command):CommandValue = new CommandValue(c)


    def itemRef(name: String): CommandValue = wrap(ItemRef(name))

    def showInv(): CommandValue = wrap(ShowInventoryAllStore())

    def showInv(item: CommandValue): CommandValue = wrap(ShowInventoryItem(item.v.asInstanceOf[ItemRef]))

}
