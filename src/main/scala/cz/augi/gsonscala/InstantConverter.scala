package cz.augi.gsonscala

import java.lang.reflect.Type
import java.time.Instant
import java.time.format.DateTimeFormatter

import com.google.gson.{JsonDeserializationContext, _}

object InstantAsUnixSecondsConverter extends JsonSerializer[Instant] with JsonDeserializer[Instant] {
  def serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.getEpochSecond)
  def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Instant = Instant.ofEpochSecond(json.getAsLong)
}

object InstantAsUnixMillisConverter extends JsonSerializer[Instant] with JsonDeserializer[Instant] {
  def serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toEpochMilli)
  def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Instant = Instant.ofEpochMilli(json.getAsLong)
}

object InstantAsStringConverter extends JsonSerializer[Instant] with JsonDeserializer[Instant] {
  def serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(DateTimeFormatter.ISO_INSTANT.format(src))
  def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(json.getAsString))
}
