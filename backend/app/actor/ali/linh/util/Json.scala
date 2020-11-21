package actor.ali.linh.util
import org.json4s._
import org.json4s.ext.{EnumNameSerializer, EnumSerializer}
object Json {

    implicit val formats: Formats = DefaultFormats + new EnumSerializer

    def render(value: AnyRef):String = jackson.compactJson( Extraction.decompose(value).underscoreKeys )

    def renderPretty(value: AnyRef):String = jackson.prettyJson( Extraction.decompose(value).underscoreKeys )

    def parse[T](json: String)(implicit m: Manifest[T]):T = {
        Extraction.extract[T](jackson.parseJson(json).camelizeKeys)
    }

    //Doesn't change the keys - e.g underscoring / lowercasing
    def parseAsIs[T](json: String)(implicit m: Manifest[T]):T = {
        Extraction.extract[T](jackson.parseJson(json))
    }

    //Doesn't change keys - e.g underscoring / lowercasing
    def renderPrettyAsIs(value: AnyRef):String = jackson.prettyJson( Extraction.decompose(value) )


    private class EnumSerializer() extends Serializer[Enum[_]] {
        override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Enum[_]] = {
            // using Json4sFakeEnum is a huge HACK here but it seems to  work
            case (TypeInfo(clazz, _), JString(name)) if classOf[Enum[_]].isAssignableFrom(clazz) => Enum.valueOf[Json4sFakeEnum](clazz.asInstanceOf[Class[Json4sFakeEnum]], name)
        }

        override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
            case v: Enum[_] => JString(v.name())
        }
    }
}
