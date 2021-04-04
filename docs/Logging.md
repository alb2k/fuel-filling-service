# Logging
To protocol informations about events in the app, some kind of logging is required.<br>
For Java therefore multiple logging frameworks exist. Some popular ones include SLF4J, LOG4J, JUL, Logback, etc.

Helidon uses [JUL](https://docs.oracle.com/javase/8/docs/api/java/util/logging/package-summary.html) as default logger.

### Using another logging framework
However I prefer the more statisfying [SLF4J](http://www.slf4j.org/) (→ https://stackoverflow.com/a/11360517) in combination with [Log4j 2](https://logging.apache.org/log4j/2.x/).<br>
So an adapter was needed to redirect JUL to SLF4J...

Luckily I found [this great guide about how to use Heldion with other logging frameworks](https://medium.com/helidon/helidon-logging-and-mdc-5de272cf085d) which basically tells you how to achieve that.

#### TL;DR / What was done to add it to this project?
* Added the following dependencies:
  * [io.helidon.logging:helidon-logging-slf4j](https://mvnrepository.com/artifact/io.helidon.logging/helidon-logging-slf4j) (Helidon SLF4J integration)
  * [org.slf4j:slf4j-api](https://mvnrepository.com/artifact/org.slf4j/slf4j-api) (logging facade)
  * [org.slf4j:jul-to-slf4j](https://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j) (JUL → SLF4J)
  * [org.apache.logging.log4j:log4j-slf4j-impl](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j-impl) (SLF4J → LOG4J)
  * [org.apache.logging.log4j:log4j-core](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core) ("real" logging framework)
* Added [log4j2.xml](../src/main/resources/log4j2.xml) which [configures the logging framework](https://logging.apache.org/log4j/2.x/manual/configuration.html)
* Tweaked the [logging.properties](../src/main/resources/logging.properties) a bit so that it works correctly
* Due to no available main entry class the SLF4JBridgeHandler for JUL is installed in the [Startup class](../src/main/java/hackathon/microstream/Startup.java#L26-L30)

After everything was done the output looks like this:
```
"...java.exe" ... io.helidon.microprofile.cdi.Main
[INFORMATION] 2021-04-03 20:31:47 [Thread[main,5,main]] Logging at initialization configured using classpath: /logging.properties 
[INFO ] 2021-04-03 20:31:48.476 [main] org.jboss.weld.Version - WELD-000900: 3.1.6 (Final)
[INFO ] 2021-04-03 20:31:49.309 [main] org.jboss.weld.Bootstrap - WELD-ENV-000020: Using jandex for bean discovery
[INFO ] 2021-04-03 20:31:49.531 [main] org.jboss.weld.Bootstrap - WELD-000101: Transactional services not available. Injection of @Inject UserTransaction not available. Transactional observers will be invoked synchronously.
[INFO ] 2021-04-03 20:31:49.679 [main] org.jboss.weld.Event - WELD-000411: Observer method [BackedAnnotatedMethod] public org.glassfish.jersey.ext.cdi1x.internal.ProcessAllAnnotatedTypes.processAnnotatedType(@Observes ProcessAnnotatedType<?>, BeanManager) receives events for all annotated types. Consider restricting events using @WithAnnotations or a generic type with bounds.
[INFO ] 2021-04-03 20:31:49.694 [main] org.jboss.weld.Event - WELD-000411: Observer method [BackedAnnotatedMethod] private io.helidon.microprofile.openapi.OpenApiCdiExtension.processAnnotatedType(@Observes ProcessAnnotatedType<X>) receives events for all annotated types. Consider restricting events using @WithAnnotations or a generic type with bounds.
[INFO ] 2021-04-03 20:31:50.465 [main] hackathon.microstream.Startup - The application is starting...
[INFO ] 2021-04-03 20:31:50.465 [main] hackathon.microstream.Startup - Installing org.slf4j.bridge.SLF4JBridgeHandler
[INFO ] 2021-04-03 20:31:50.465 [main] hackathon.microstream.Startup - Installed org.slf4j.bridge.SLF4JBridgeHandler successfully
[INFO ] 2021-04-03 20:31:50.465 [main] hackathon.microstream.Startup - Initializing DB
[INFO ] 2021-04-03 20:31:50.465 [main] hackathon.microstream.storage.DBManager - Loading configuration
[INFO ] 2021-04-03 20:31:51.020 [main] hackathon.microstream.storage.DBManager - BaseDirectory is 'data'
[INFO ] 2021-04-03 20:31:51.020 [main] hackathon.microstream.storage.DBManager - Initializing storageManager
[INFO ] 2021-04-03 20:31:51.553 [main] hackathon.microstream.storage.DBManager - Demo mode; Using demo data
[INFO ] 2021-04-03 20:31:51.553 [main] hackathon.microstream.Startup - Initialized DB
[INFO ] 2021-04-03 20:31:51.553 [main] hackathon.microstream.Startup - The application is started
[INFO ] 2021-04-03 20:31:51.553 [main] io.helidon.microprofile.server.ServerCdiExtension - Registering JAX-RS Application: App
[INFO ] 2021-04-03 20:31:52.217 [main] org.hibernate.validator.internal.util.Version - HV000001: Hibernate Validator 6.1.2.Final
[INFO ] 2021-04-03 20:31:53.253 [nioEventLoopGroup-2-1] io.helidon.webserver.NettyWebServer - Channel '@default' started: [id: 0x245155bc, L:/0:0:0:0:0:0:0:0:8080]
[INFO ] 2021-04-03 20:31:53.253 [main] io.helidon.microprofile.server.ServerCdiExtension - Server started on http://localhost:8080 (and all other host addresses) in 6257 milliseconds (since JVM startup).
[INFO ] 2021-04-03 20:31:53.268 [features-thread] io.helidon.common.HelidonFeatures - Helidon MP 2.2.1 features: [CDI, Config, Health, JAX-RS, Open API, Server]
...
```
