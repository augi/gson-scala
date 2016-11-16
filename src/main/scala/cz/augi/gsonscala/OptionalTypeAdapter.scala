package cz.augi.gsonscala

import java.lang.reflect.ParameterizedType
import java.util.Optional

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.{JsonReader, JsonWriter}
import com.google.gson.{Gson, TypeAdapter, TypeAdapterFactory}

object OptionalTypeAdapterFactory extends TypeAdapterFactory {
  override def create[T](gson: Gson, t: TypeToken[T]): TypeAdapter[T] =
    if (classOf[Optional[_]].isAssignableFrom(t.getRawType)) new OptionalTypeAdapter(gson, t) else null
}

class OptionalTypeAdapter[T](gson: Gson, t: TypeToken[T]) extends TypeAdapter[T] {
  val innerType = t.getType.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)

  override def write(out: JsonWriter, value: T): Unit =
    value match {
      case o: Optional[_] =>
        if (o.isPresent) {
          gson.toJson(o.get, innerType, out)
        } else {
          // we must forcibly write null in order the read method to be called
          val orig = out.getSerializeNulls
          out.setSerializeNulls(true)
          out.nullValue()
          out.setSerializeNulls(orig)
        }
    }

  override def read(in: JsonReader): T = Optional.ofNullable(gson.fromJson(in, innerType)).asInstanceOf[T]
}
