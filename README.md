# ALMI (Abstraction Layer for Multiserver Infrastructure)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.matteobattilana/almi/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.matteobattilana/almi)
[![CircleCI](https://circleci.com/gh/MatteoBattilana/ALMI.svg?style=svg)](https://circleci.com/gh/MatteoBattilana/ALMI)

## Description
ALMI is a Java multiserver framework for remote method calls management based on Netty and Jackson serialization, that adds an abstraction layer for multiserver communication.

This framework will semplify the cluster management, making methods accessibile from other server, that has ALMI running on a node in the cluster.

## Setup
Add ALMI dependencies in your pom.xml or eventually, go to the [Maven Repository](https://search.maven.org/artifact/com.matteobattilana/almi/0.1.0/jar) and download the jar library file.

```xml
<dependencies>
    <dependency>
        <groupId>com.matteobattilana</groupId>
        <artifactId>almi</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

## Simple usage

The following code will instantiate an ALMI server that is listening on the 8888 port. If not set, it will use the one described in the [Default configuration](#default-configuration) section).

```java
Almi server = AlmiBootstrap.bootstrap()
          .withPort(8888)
          .withRemoteCallTimeout(2000)
          .withMethodsMapper(new MethodsMapper()
          {
              @Override
              public void configure()
                throws Exception
              {
                  addMethods(
                    bindStatic(Calculator.class).method("execute", double.class, Calculator.Operation.class, double.class).withDefaultName(),
                    bind(new Calculator()).method(Calculator.class.getMethod("sqrt", double.class)).withName("positiveSqrt")
                  );
              }
          })
          .start();
```

### MethodsMapper
MethodsMapper will let you define the mapping beetwen the local method implementation and its remote name. This is used to make methods accessibile from other ALMI server, running on a node in the cluster.

A MethodsMapper can be defined in-line as the one in previous example, or in a specific class. The `addMethods` will configure the methods passed as arguments, in order to expose them.
```java
public class MethodMapperImpl extends MethodsMapper
{
    private final ClientInformation mInstance1;

    public MethodMapperImpl(
      ClientInformation instance1
    )
    {
        mInstance1 = instance1;
    }

    @Override
    public void configure()
      throws Exception
    {
        addMethods(
          bind(mInstance1).method(ClientInformation.class.getMethod("helloWorld")).withDefaultName(),
          bindStatic(ClientInformation.class).method(ClientInformation.class.getMethod("ping")).withDefaultName()
          );
    }
}
```


## Configuration
### Default configuration
The defaul configuration loaded from static constants from the Constants.java:

```java
public static final int    DEFAULT_PORT               = 8888;
public static final int    DEFAULT_CONNECTION_TIMEOUT = 10000;
public static final int    DEFAULT_PROMISE_TIMEOUT    = 10000;
public static final String DEFAULT_THREAD_NAME        = "almi-service";
```
