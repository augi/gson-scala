# gson-scala

[![Build Status](https://travis-ci.org/augi/gson-scala.svg?branch=master)](https://travis-ci.org/augi/gson-scala) [![Download](https://api.bintray.com/packages/augi/maven/gson-scala_2.12/images/download.svg) ](https://bintray.com/augi/maven/gson-scala_2.12/_latestVersion)

Allows to use Scala and Java 8 types with [gson library](https://github.com/google/gson).

Supported types:
* `java.time.Instant`
* `java.time.Duration`
* `scala.concurrent.duration.Duration`
* `java.util.Optional`
* `scala.Option`
* `scala.Seq`

## How to use
```xml
<dependency>
    <groupId>cz.augi.gsonscala</groupId>
    <artifactId>gson-scala_2.12</artifactId>
    <version>$latestVersion</version>
</dependency>
```

```gradle
compile "cz.augi.gsonscala:gson-scala_2.12:$latestVersion"
```

```scala
import cz.augi.gsonscala._
val gson = new GsonBuilder()
                .registerBasicConverters() // registers for Optional, Option and Seq
                .registerMillisDurationConverters() // registers for Duration classes expecting millis in integer values
                .registerUnixMillisInstantConverter() // registers for Instant expecting Unix millis in integer values
                .create()
```

Alternatively, these methods can be used for registrations:
* `registerSecondsDurationConverters` - expects durations in seconds
* `registerStringDurationConverters`
* `registerUnixSecondsInstantConverter` - expects time in Unix timestamp (seconds)
* `registerStringInstantConverter` - expects time in ISO format

You can also cherry-pick some of converters [as shown here](https://github.com/augi/gson-scala/blob/master/src/main/scala/cz/augi/gsonscala/package.scala).

## Why to use this library?
As gson library targets Java 6, it doesn't support Java 8 type out of the box. There are several libraries that add support
for `Optional` type but most of them is not able to handle missing value correctly.
 
For example [gson-java8-datatype](https://github.com/caoqianli/gson-java8-datatype) looked promising but
it has [too simple tests](https://github.com/caoqianli/gson-java8-datatype/blob/master/src/test/java/net/dongliu/gson/GsonJava8TypeAdapterFactoryTest.java).
E.g. if you have a class with `Optional` field then the deserialized object contains `null` instead of `Optional.empty()`.

The problem is that `gson` by default doesn't write `null` values. So the `read` method of `TypeAdapter` is not even called for missing value
and the field of deserialized object has `null` value. Tests of `gson-java8-datatype` are passing because `null` value is written if it's top-level object.
