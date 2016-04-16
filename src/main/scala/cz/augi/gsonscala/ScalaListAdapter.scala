package cz.augi.gsonscala

import java.lang.reflect.{ParameterizedType, Type}

import com.google.gson._

object ScalaListAdapter extends JsonSerializer[List[_]] with JsonDeserializer[List[_]] {
  import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
  import scala.collection.JavaConverters._

  private def scalaListTypeToJava(t: ParameterizedType): ParameterizedType = {
    ParameterizedTypeImpl.make(classOf[java.util.List[_]], t.getActualTypeArguments, null)
  }

  override def serialize(src: List[_], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val p = scalaListTypeToJava(typeOfSrc.asInstanceOf[ParameterizedType]) // Safe casting because List is a ParameterizedType.
    context.serialize(src.asInstanceOf[List[Any]].asJava, p)
  }

  override def deserialize(jsonElement: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List[_] = {
    val p = scalaListTypeToJava(typeOfT.asInstanceOf[ParameterizedType]) // Safe casting because List is a ParameterizedType.
    val javaList: java.util.List[_ <: Any] = context.deserialize(jsonElement, p)
    javaList.asScala.toList
  }
}
