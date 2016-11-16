package cz.augi.gsonscala

import java.util.Optional
import java.util.concurrent.TimeUnit

import com.google.gson.{Gson, GsonBuilder}
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

import scala.concurrent.duration.Duration

class AsMillis extends UnitSpec {
  val gson = new GsonBuilder()
              .registerMillisDurationConverters()
              .registerUnixMillisInstantConverter()
              .registerBasicConverters()
              .create()
}

class AsSeconds extends UnitSpec {
  val gson = new GsonBuilder()
              .registerSecondsDurationConverters()
              .registerUnixSecondsInstantConverter()
              .registerBasicConverters()
              .create()
}

class AsString extends UnitSpec {
  val gson = new GsonBuilder()
              .registerStringDurationConverters()
              .registerStringInstantConverter()
              .registerBasicConverters()
              .create()
}

@RunWith(classOf[JUnitRunner])
abstract class UnitSpec extends FlatSpec with Matchers {
  def gson: Gson

  private case class ExampleClass(
                       instant: java.time.Instant,
                       duration: java.time.Duration,
                       scalaDuration: Duration,
                       optional: Optional[String],
                       option: Option[String],
                       seq: Seq[Int])

  "Converters" must "serialize and deserialize" in {
    val input = ExampleClass(
                  java.time.Instant.ofEpochMilli(123456000),
                  java.time.Duration.ofMillis(123000),
                  Duration(456, TimeUnit.SECONDS),
                  Optional.of("this is optional string"),
                  Some("this is option string"),
                  Seq(1, 2, 3))
    val json = gson.toJson(input)
    val deserialized: ExampleClass = gson.fromJson(json, input.getClass)
    deserialized shouldEqual input
  }

  it must "handle missing values" in {
    val input = ExampleClass(
                  java.time.Instant.ofEpochMilli(123456000),
                  java.time.Duration.ofMillis(123000),
                  Duration(456000, TimeUnit.MILLISECONDS),
                  Optional.empty(),
                  None,
                  Seq.empty)
    val json = gson.toJson(input)
    val deserialized: ExampleClass = gson.fromJson(json, input.getClass)
    deserialized shouldEqual input
  }

  implicit class ExampleClassMatcher(actual: ExampleClass) {
    def shouldEqual(expected: ExampleClass): Unit = {
      actual.instant shouldBe expected.instant
      actual.duration shouldBe expected.duration
      actual.scalaDuration shouldBe expected.scalaDuration
      actual.optional shouldBe expected.optional
      actual.option shouldBe expected.option
      actual.seq shouldEqual expected.seq
    }
  }
}
