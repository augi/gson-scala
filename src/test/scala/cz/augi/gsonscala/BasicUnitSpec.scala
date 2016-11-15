package cz.augi.gsonscala

import com.google.gson.GsonBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BasicUnitSpec extends FlatSpec {
  val gson = new GsonBuilder()
    .registerBasicConverters()
    .create()

  private case class ExampleClass(value: Option[String])

  it must "handle missing properties" in {
    gson.fromJson("{}", classOf[ExampleClass]) should matchPattern {
      case ExampleClass(None) =>
    }
  }
}
