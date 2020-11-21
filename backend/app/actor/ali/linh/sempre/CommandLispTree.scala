package actor.ali.linh.sempre

import actor.ali.linh.input.Command

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger
import fig.basic.LispTree

class CommandLispTree(val cmd: Command) extends LispTree{

    private val log = Logger[CommandLispTree]
}
