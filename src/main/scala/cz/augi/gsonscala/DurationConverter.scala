package cz.augi.gsonscala

import java.lang.reflect.Type
import java.time.Duration

import com.google.gson.{JsonDeserializationContext, _}

object DurationAsMillisConverter extends DurationConverterBase {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toMillis)
}

object DurationAsStringConverter extends DurationConverterBase {
  override def serialize(src: Duration, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = new JsonPrimitive(src.toString)
}

abstract class DurationConverterBase extends JsonSerializer[Duration] with JsonDeserializer[Duration] {
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Duration = json match {
    case primitive: JsonPrimitive if primitive.isNumber => Duration.ofMillis(primitive.getAsLong)
    case j => Duration.parse(j.getAsString)
  }
}
