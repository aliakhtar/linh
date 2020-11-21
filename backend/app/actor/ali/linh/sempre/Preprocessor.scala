package actor.ali.linh.sempre

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.Logger

object Preprocessor {

    private val syns = Map(
        "show" -> Set("display", "view", "render", "check", "show"),
        "inventory" -> Set("inv", "stock", "quantity", "qty", "inventories", "stocks"),
        "left" -> Set("remaining"),
        "are" -> Set("is"),
        "many" -> Set("much"),
    )


    def getSyns(word: String):Set[String] = {
        syns.getOrElse(word, Set())
    }

    def mapSyn(token: String):String = {
        if (syns.isDefinedAt(token))
            return token

        syns.find(t => t._2.contains(token))
            .map(_._1)
            .getOrElse(token)
    }

    def process(input: String):String = {
        val tokens = input.trim.toLowerCase.split(" ")
        tokens.map(mapSyn).mkString(" ")
    }

}
