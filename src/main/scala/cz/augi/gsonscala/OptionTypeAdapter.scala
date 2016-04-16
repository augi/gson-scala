package cz.augi.gsonscala

import java.lang.reflect.ParameterizedType

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.{JsonReader, JsonWriter}
import com.google.gson.{Gson, TypeAdapter, TypeAdapterFactory}

object OptionTypeAdapterFactory extends TypeAdapterFactory {
  override def create[T](gson: Gson, t: TypeToken[T]): TypeAdapter[T] =
    if (classOf[Option[_]].isAssignableFrom(t.getRawType)) new OptionTypeAdapter(gson, t) else null
}

class OptionTypeAdapter[T](gson: Gson, t: TypeToken[T]) extends TypeAdapter[T] {
  val innerType = t.getType.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)

  override def write(out: JsonWriter, value: T): Unit =
    value match {
      case o: Option[_] => o match {
        case Some(v) => gson.toJson(v, innerType, out)
        case None =>
          // we must forcibly write null in order the read method to be called
          val orig = out.getSerializeNulls
          out.setSerializeNulls(true)
          out.nullValue()
          out.setSerializeNulls(orig)
      }
    }

  override def read(in: JsonReader): T = Option(gson.fromJson(in, innerType)).asInstanceOf[T]
}
