package cz.augi.gsonscala

import java.util.Optional

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.{JsonReader, JsonWriter}
import com.google.gson.{Gson, TypeAdapter, TypeAdapterFactory}

object NonNullTypeAdapterFactory extends TypeAdapterFactory {
  override def create[T](gson: Gson, t: TypeToken[T]): TypeAdapter[T] = {
    if (t.getRawType.isPrimitive) {
      null
    } else {
      val adapter = new NonNullTypeAdapter[T](t.getRawType, gson.getDelegateAdapter(this, t))
      if (adapter.fields.nonEmpty) adapter else null
    }
  }

  def getDefaultValue(t: Class[_]): Any =
    if (classOf[Option[_]].isAssignableFrom(t)) Option.empty
    else if (classOf[Optional[_]].isAssignableFrom(t)) Optional.empty()
    else if (classOf[Seq[_]].isAssignableFrom(t)) Seq.empty
    else null

  class NonNullTypeAdapter[T](c: Class[_ >: T], delegate: TypeAdapter[T]) extends TypeAdapter[T] {
    val fields = c.getDeclaredFields
                  .map(f => (f, getDefaultValue(f.getType)))
                  .filter(_._2 != null)
    fields.foreach { case (f, _) => if (!f.isAccessible) f.setAccessible(true) }

    override def write(out: JsonWriter, value: T): Unit = delegate.write(out, value)

    override def read(in: JsonReader): T = {
      val r = delegate.read(in)
      fields.foreach { case (f, dv) => if (f.get(r) == null) f.set(r, dv) }
      r
    }
  }
}
