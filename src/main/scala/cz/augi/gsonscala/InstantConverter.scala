package cz.augi.gsonscala

import java.lang.reflect.Type
import java.time.Instant
import java.time.format.DateTimeFormatter

import com.google.gson.{JsonDeserializationContext, _}

object InstantConverter extends JsonSerializer[Instant] with JsonDeserializer[Instant] {
  override def serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toEpochMilli)

  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Instant = json match {
    case primitive: JsonPrimitive if primitive.isNumber => Instant.ofEpochMilli(primitive.getAsLong)
    case j => Instant.from(DateTimeFormatter.ISO_INSTANT.parse(j.getAsString))
  }
}
