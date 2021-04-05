# Helidon (Microprofile)

[Helidon MP](https://helidon.io/docs/latest/#/about/02_introduction) is a [collection of Java Libaries](https://alb2k.github.io/fuel-filling-service/dependencies/#Dependency_Tree) mainly used for Java/Jakarte EE conform web/microservices.<br>
This project is based on [helidon-quickstart-mp](https://github.com/oracle/helidon/tree/master/examples/quickstarts/helidon-quickstart-mp).

## Webservice
![Heldion_Overview.png](https://user-images.githubusercontent.com/80211953/113520281-34e32f80-9592-11eb-8ea8-6d5d118864b2.png)

The HTTP Webservice was done with [JAX-RS](https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services) and other [Java/Jakarta EE technologies](https://en.wikipedia.org/wiki/Jakarta_EE).<br>
The "rest resources" (which represent the HTTP interface) are located in [src/main/java/hackathon/microstream/service/rest/resource](../src/main/java/hackathon/microstream/service/rest/resource).

#### Validation
If entities are transmitted to a method they are validated (via the ``@Valid`` annotation) using [jersey-bean-validation](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/bean-validation.html).<br>
When the validation fails, a ``ConstraintViolationException`` is thrown which is automatically processed by the [ConstraintViolationExceptionMapper](../src/main/java/hackathon/microstream/service/system/ConstraintViolationExceptionMapper.java) and results in a better feedback:
```
Unrecognized token 'trues': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
 at [Source: (org.glassfish.jersey.message.internal.ReaderInterceptorExecutor$UnCloseableInputStream); line: 7, column: 30]
```

#### Serialization
For the serialization from JSON to Java objects and back [com.fasterxml.jackson](https://github.com/FasterXML/jackson) is used.
This worked pretty good, however some tweaks had to be [applied to the defaults](../src/main/java/hackathon/microstream/service/system/ObjectMapperContextResolver.java):
* By default LocalDate is written as array, which is not [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601) conform<br>
 → https://stackoverflow.com/a/28803634
* By default ObjectMapper throws an exception, if unknown properties are supplied.<br>
 To be a bit less restrictive, unknown properties are allowed (easier handling of objects with / without UUID)
 
To also automatically serialize Java objects (to JSON) in a response without the need of doing it manually with an ObjectMapper [org.glassfish.jersey.media:jersey-media-json-jackson](https://mvnrepository.com/artifact/org.glassfish.jersey.media/jersey-media-json-jackson) is used.<br>
More information [available here](https://stackoverflow.com/questions/26207252/messagebodywriter-not-found-for-media-type-application-json).

#### CDI
The corresponding [service](../src/main/java/hackathon/microstream/service/provider) is injected into the rest resource via CDI.

#### Microprofile
Furthermore the integrated webserver can be configured with microprofile.<br>
For example, the [``server.port`` can be set](../src/main/resources/META-INF/microprofile-config.properties#L4-L6).

#### Other tweaks
* When a entity is not found, a [``NotFoundException``](../src/main/java/hackathon/microstream/dal/util/NotFoundException.java) is thrown, which is automatically catched by the [``NotFoundExceptionHandler``](../src/main/java/hackathon/microstream/service/rest/errorhandling/NotFoundExceptionHandler.java)
