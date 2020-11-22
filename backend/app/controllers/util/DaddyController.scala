package controllers.util

import actor.ali.linh.response.{Data, Response}
import play.api.libs.json.{JsError, Json, OWrites}
import play.api.mvc.{AbstractController, ControllerComponents, Result}


abstract class DaddyController(cc: ControllerComponents)
    extends AbstractController(cc){

    implicit val respWriter: OWrites[Response] = Json.writes[Response]

    protected val jsonMimeType = "application/json"


    protected def success():Result = {
        ok(Response.Success)
    }

    protected def ok(entity: AnyRef ): Result = {
        Ok( render(entity) )
                .as(jsonMimeType).withHeaders("Allow-Origin" -> "*")
    }

    // Used by fitbit webhook endpoint: https://dev.fitbit.com/build/reference/web-api/subscriptions/#verify-a-subscriber
    protected def noContent(): Result = NoContent

    // USed by fitbit webhook endpoint: https://dev.fitbit.com/build/reference/web-api/subscriptions/#verify-a-subscriber
    protected def pageNotFound(): Result = NotFound

    protected def oops(e: Throwable) : Result = {
        val resp = Response.errors(e)
        UnprocessableEntity(Json.toJson(resp))
                .as(jsonMimeType).withHeaders("Allow-Origin" -> "*")
    }


    protected def oops(e: String) : Result = {
        val resp = Response.errors(e)
        UnprocessableEntity(Json.toJson(resp))
          .as(jsonMimeType).withHeaders("Allow-Origin" -> "*")
    }

    protected def render(item: AnyRef) = actor.ali.linh.util.Json.render(item)

    /**
      * Wraps the given argument inside a Data object, so the resulting json will have `data` as the root element.
      * As requested by FE.
      */
    protected def asData[T](result: T): Data[T] = Data(result)
}
