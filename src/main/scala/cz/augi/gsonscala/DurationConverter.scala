package cz.augi.gsonscala

import java.lang.reflect.Type
import java.time.Duration

import com.google.gson.{JsonDeserializationContext, _}

object DurationAsMillisConverter  extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toMillis)
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = Duration.ofMillis(json.getAsLong)
}

object DurationAsSecondsConverter  extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.getSeconds)
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = Duration.ofSeconds(json.getAsLong)
}

object DurationAsStringConverter extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toString)
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = Duration.parse(json.getAsString)
}
