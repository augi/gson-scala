package cz.augi

import com.google.gson.GsonBuilder

package object gsonscala {
  implicit class GsonScalaRegistrator(val gsonBuilder: GsonBuilder) extends AnyVal {

    def registerScalaMillisConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapter(classOf[java.time.Instant], InstantAsMillisConverter)
      .registerTypeAdapter(classOf[java.time.Duration], DurationAsMillisConverter)
      .registerTypeAdapter(classOf[scala.concurrent.duration.Duration], ScalaDurationConverter)
      .registerTypeAdapterFactory(OptionalTypeAdapterFactory)
      .registerTypeAdapterFactory(OptionTypeAdapterFactory)
      .registerTypeAdapterFactory(SeqTypeAdapterFactory)

    def registerScalaStringConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapter(classOf[java.time.Instant], InstantAsStringConverter)
      .registerTypeAdapter(classOf[java.time.Duration], DurationAsStringConverter)
      .registerTypeAdapter(classOf[scala.concurrent.duration.Duration], ScalaDurationConverter)
      .registerTypeAdapterFactory(OptionalTypeAdapterFactory)
      .registerTypeAdapterFactory(OptionTypeAdapterFactory)
      .registerTypeAdapterFactory(SeqTypeAdapterFactory)

  }
}
