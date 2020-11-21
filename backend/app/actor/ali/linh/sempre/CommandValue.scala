package actor.ali.linh.sempre

import actor.ali.linh.input.Command

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger
import edu.stanford.nlp.sempre.Value
import fig.basic.LispTree

case class CommandValue(v: Command) extends Value{

    override def equals(var1: Any): Boolean = (this.v == var1)

    override def hashCode: Int = this.v.hashCode()

    override def toLispTree: LispTree = new CommandLispTree(v)

    override def toString: String = v.toString
}
