package actor.ali.linh.util

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream, PrintWriter}

import com.typesafe.scalalogging.Logger

import scala.io.{Codec, Source}

//noinspection DuplicatedCode
object IO {

    private val log = Logger("IO")


    def readResource(pathWithoutSlash: String):Iterable[String] = {

        log.info(s"Loading resource: $pathWithoutSlash")
        val source = Source.fromResource(pathWithoutSlash)(Codec.UTF8)
        val lines:Seq[String] = (for (l <- source.getLines() if ! l.trim.isEmpty) yield l.trim).toList
        log.info(s"Got ${lines.size} lines")

        //log.trace("Closing source..")
        source.close()
        lines
    }

    def readFile(filePath: String):Iterable[String] = {

        log.trace(s"Loading file: $filePath")
        val source = Source.fromFile(filePath)
        val lines:Seq[String] = (for (l <- source.getLines() if ! l.trim.isEmpty) yield l.trim).toList
        log.trace(s"Got ${lines.size} lines")

        log.trace("Closing source..")
        source.close()
        lines
    }


    def serialize(item: Any, file: String): Unit = {
        val oos = new ObjectOutputStream(new FileOutputStream(file))
        oos.writeObject(item)
        oos.close()
    }

    def deserialize[T] (path: String, clazz: Class[T] ): T = {
        val ois = new ObjectInputStream(new FileInputStream(path))
        val result:T = ois.readObject.asInstanceOf[T]
        ois.close()

        result
    }

    def write(path: String, text: String): Unit = {
        log.info(s"Writing to $path")
        val writer = new PrintWriter(new File(path))
        writer.write(text)
        writer.close()
    }

    def append(path: String, text: String): Unit = {
        log.info(s"Writing to $path")
        val writer = new PrintWriter(new File(path))
        writer.append(text)
        writer.close()
    }
}
