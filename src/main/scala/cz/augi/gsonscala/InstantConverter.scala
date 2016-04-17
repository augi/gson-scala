package cz.augi.gsonscala

import java.lang.reflect.Type
import java.time.Instant
import java.time.format.DateTimeFormatter

import com.google.gson.{JsonDeserializationContext, _}

object InstantAsMillisConverter extends InstantConverterBase {
  override def serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toEpochMilli)
}

object InstantAsStringConverter extends InstantConverterBase {
  override def serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(DateTimeFormatter.ISO_INSTANT.format(src))
}

abstract class InstantConverterBase extends JsonSerializer[Instant] with JsonDeserializer[Instant] {
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Instant = json match {
    case primitive: JsonPrimitive if primitive.isNumber => Instant.ofEpochMilli(primitive.getAsLong)
    case j => Instant.from(DateTimeFormatter.ISO_INSTANT.parse(j.getAsString))
  }
}
