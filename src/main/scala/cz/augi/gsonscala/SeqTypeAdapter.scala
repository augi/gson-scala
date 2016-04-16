package cz.augi.gsonscala

import java.lang.reflect.ParameterizedType

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.{JsonReader, JsonToken, JsonWriter}
import com.google.gson.{Gson, TypeAdapter, TypeAdapterFactory}

object SeqTypeAdapterFactory extends TypeAdapterFactory {
  override def create[T](gson: Gson, t: TypeToken[T]): TypeAdapter[T] =
    if (classOf[Seq[_]].isAssignableFrom(t.getRawType)) new SeqTypeAdapter(gson, t) else null
}

class SeqTypeAdapter[T](gson: Gson, t: TypeToken[T]) extends TypeAdapter[T] {
  val innerType = t.getType.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)

  override def write(out: JsonWriter, value: T): Unit = {
    out.beginArray()
    value.asInstanceOf[Seq[_]].foreach(i => gson.toJson(i, innerType, out))
    out.endArray()
  }

  override def read(in: JsonReader): T = {
    val builder = scala.collection.mutable.Seq.newBuilder
    in.beginArray()
    while(in.peek() != JsonToken.END_ARRAY) {
      builder += gson.fromJson(in, innerType)
    }
    in.endArray()
    val res = builder.result()
    (if (res.isEmpty) Seq.empty else res).asInstanceOf[T]
  }
}
