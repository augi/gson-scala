package cz.augi.gsonscala

import java.util.Optional

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.{JsonReader, JsonWriter}
import com.google.gson.{Gson, TypeAdapter, TypeAdapterFactory}

object NonNullTypeAdapterFactory extends TypeAdapterFactory {
  override def create[T](gson: Gson, t: TypeToken[T]): TypeAdapter[T] = {
    if (!classOf[Any].isAssignableFrom(t.getRawType)) {
      null
    } else {
      new NonNullTypeAdapter[T](t.getRawType, gson.getDelegateAdapter(this, t))
    }
  }

  def getDefaultValue(t: Class[_]): Any =
    if (t.isAssignableFrom(classOf[Option[_]])) Option.empty
    else if (t.isAssignableFrom(classOf[Optional[_]])) Optional.empty()
    else if (t.isAssignableFrom(classOf[Seq[_]])) Seq.empty
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
