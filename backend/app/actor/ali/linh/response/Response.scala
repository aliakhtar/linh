package actor.ali.response

import actor.ali.util.JsErrorsTranslator
import play.api.libs.json.JsError
/**
 * Represents the http response sent out if there are any validation errors.
 *
 * @param success If true, errors will be empty - this indicates a simple 'success = true' response to some operation
 *                where no other info needs to be sent out other than to acknowledge that something succeeded.
 *
 * @param errors If success = false, then this will be populated with a list of error messages.
 */
case class Response(success: Boolean, errors: Iterable[String])

case object Response
{
    val Success = Response(success = true, Nil)
    def errors(jsErr: JsError): Response = {
        val readableErrors:Iterable[String] = JsErrorsTranslator.translate(jsErr)
        Response(success = false, readableErrors)
    }

    def errors(errors: String*): Response = {
        Response(success = false, errors)
    }

    def errors(e: Throwable): Response = {
        Response(success = false, Seq(e.getMessage))
    }
}