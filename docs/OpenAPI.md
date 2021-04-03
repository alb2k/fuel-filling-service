# [OpenAPI](https://www.openapis.org/)

OpenAPI is a standardized specification to describe REST-conform APIs.

Helidon supports OpenAPI.<br>
For more information read [this guide](https://medium.com/helidon/project-helidon-and-openapi-54a1fadc75b1).

## OpenAPI UI
To test the app without the requirement of an external HTTP Client, you can simply add [OpenAPI UI](https://github.com/microprofile-extensions/openapi-ext/blob/main/openapi-ui/README.md) (derived from [Swagger UI](https://swagger.io/tools/swagger-ui/)).

#### What was done to add it to this project?
* Added the [dependency](https://mvnrepository.com/artifact/org.microprofile-ext.openapi-ext/openapi-ui) to the pom.xml
* Annotated the Rest-Endpoints with informations
* Added a custom application class for some informations → [App.java](../src/main/java/hackathon/microstream/service/system/App.java)
* Customized the UI further with [microprofile-config.properties](../src/main/resources/META-INF/microprofile-config.properties#L9-L11)
* Added a [RootResource](../src/main/java/hackathon/microstream/service/rest/resource/RootResource.java) which redirects to the OpenAPI UI endpoint when trying to get '/'

I also highly recommend reading the ["Getting Started" page of OpenAPI-UI](https://github.com/microprofile-extensions/openapi-ext/blob/main/openapi-ui/README.md#getting-started), because it explains everything that was done in detail.
