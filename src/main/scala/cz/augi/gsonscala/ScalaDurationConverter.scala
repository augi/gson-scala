package cz.augi.gsonscala

import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

import com.google.gson.{JsonDeserializationContext, _}

import scala.concurrent.duration.Duration

object ScalaDurationAsMillisConverter  extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toMillis)
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = Duration(json.getAsLong, TimeUnit.MILLISECONDS)
}

object ScalaDurationAsSecondsConverter  extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toSeconds)
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = Duration(json.getAsLong, TimeUnit.SECONDS)
}

object ScalaDurationAsStringConverter extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toString)
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = Duration(json.getAsString)
}
