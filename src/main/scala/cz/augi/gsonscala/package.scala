package cz.augi

import com.google.gson.GsonBuilder

package object gsonscala {
  implicit class GsonScalaRegistrator(val gsonBuilder: GsonBuilder) extends AnyVal {

    def registerBasicConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapterFactory(OptionalTypeAdapterFactory)
      .registerTypeAdapterFactory(OptionTypeAdapterFactory)
      .registerTypeAdapterFactory(SeqTypeAdapterFactory)

    def registerStringDurationConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapter(classOf[java.time.Duration], DurationAsStringConverter)
      .registerTypeAdapter(classOf[scala.concurrent.duration.Duration], ScalaDurationAsStringConverter)

    def registerMillisDurationConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapter(classOf[java.time.Duration], DurationAsMillisConverter)
      .registerTypeAdapter(classOf[scala.concurrent.duration.Duration], ScalaDurationAsMillisConverter)

    def registerSecondsDurationConverters(): GsonBuilder = gsonBuilder
      .registerTypeAdapter(classOf[java.time.Duration], DurationAsSecondsConverter)
      .registerTypeAdapter(classOf[scala.concurrent.duration.Duration], ScalaDurationAsSecondsConverter)

    def registerStringInstantConverter(): GsonBuilder = gsonBuilder.registerTypeAdapter(classOf[java.time.Instant], InstantAsStringConverter)
    def registerUnixMillisInstantConverter(): GsonBuilder = gsonBuilder.registerTypeAdapter(classOf[java.time.Instant], InstantAsUnixMillisConverter)
    def registerUnixSecondsInstantConverter(): GsonBuilder = gsonBuilder.registerTypeAdapter(classOf[java.time.Instant], InstantAsUnixSecondsConverter)

  }
}
