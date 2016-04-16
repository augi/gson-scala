# gson-scala

[![Build Status](https://travis-ci.org/augi/gson-scala.svg?branch=master)](https://travis-ci.org/augi/gson-scala) [![Download](https://api.bintray.com/packages/augi/maven/gson-scala/images/download.svg) ](https://bintray.com/augi/maven/gson-scala/_latestVersion)

Allows to use Scala and Java 8 types with gson library.

Supported types:
* `java.time.Instant` - serializes as a number (Unix timestamp in millis)
* `java.time.Duration` - serializes as a number (millis)
* `scala.concurrent.duration.Duration` - serializes as a number (millis)
* `java.util.Optional`
* `scala.Option`
* `scala.Seq`

# How to use
```xml
<dependency>
    <groupId>cz.augi</groupId>
    <artifactId>gson-scala_2.11</artifactId>
    <version>1.0.0</version>
</dependency>
```

```gradle
compile 'cz.augi:gson-scala_2.11:1.0.0'
```

```scala
import cz.augi.gsonscala._
val gson = new GsonBuilder().registerScalaConverters().create()
// use gson as usual
```

If you don't want to use all the converters you can cherry-pick some of them [as shown here](https://github.com/augi/gson-scala/blob/master/src/main/scala/cz/augi/gsonscala/package.scala).

# Why to use this library?
As gson library targets Java 6, it doesn't support Java 8 type out of box. There are several libraries that add support
for `Optional` type but most of them is not able to handle missing value correctly.
 
For example [gson-java8-datatype](https://github.com/caoqianli/gson-java8-datatype) looked promising but
it has [too simple tests](https://github.com/caoqianli/gson-java8-datatype/blob/master/src/test/java/net/dongliu/gson/GsonJava8TypeAdapterFactoryTest.java).
 
For example, if you have a class with `Optional` field then the deserialized object contains `null` instead of `Optional.empty()`.

The problem is that `gson` by default doesn't write `null` values and so the `read` method of `TypeAdapter` in not even called for missing value
and so the field has `null` value. Tests of `gson-java8-datatype` are passing because `null` value is written if it's top-level object.
