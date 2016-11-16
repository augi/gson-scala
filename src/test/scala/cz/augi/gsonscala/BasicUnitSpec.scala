package cz.augi.gsonscala

import com.google.gson.GsonBuilder
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BasicUnitSpec extends FlatSpec with Matchers {

  val gson = new GsonBuilder().registerTypeAdapterFactory(NonNullTypeAdapterFactory).create()

  private case class ExampleClass(value: Option[String])

  it must "handle missing property" in {
    gson.fromJson("{}", classOf[ExampleClass]).value shouldBe None
  }
}
