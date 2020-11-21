package actor.ali.linh.input

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

sealed trait Command
case class ShowInventory(val x:String) extends Command
