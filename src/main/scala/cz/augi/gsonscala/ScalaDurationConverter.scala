package cz.augi.gsonscala

import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

import com.google.gson.{JsonDeserializationContext, _}

import scala.concurrent.duration.Duration

object ScalaDurationConverter extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toMillis)

  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = json match {
    case primitive: JsonPrimitive if primitive.isNumber => Duration(primitive.getAsLong, TimeUnit.MILLISECONDS)
    case j => Duration(j.getAsString)
  }
}
