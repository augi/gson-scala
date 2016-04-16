package cz.augi

import com.google.gson.GsonBuilder

package object gsonscala {
  implicit class GsonScalaRegistrator(val gsonBuilder: GsonBuilder) extends AnyVal {
    def registerScalaConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapter(classOf[java.time.Instant], InstantConverter)
      .registerTypeAdapter(classOf[java.time.Duration], DurationConverter)
      .registerTypeAdapter(classOf[scala.concurrent.duration.Duration], ScalaDurationConverter)
      .registerTypeAdapterFactory(OptionalTypeAdapterFactory)
      .registerTypeAdapterFactory(OptionTypeAdapterFactory)
      .registerTypeAdapterFactory(SeqTypeAdapterFactory)
  }
}
