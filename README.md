# fuel-filling-service
A helidon (microprofile) RESTful webservice with microstream.

The project represents a basic CRUD webservice where you can manage fuel fillings (of a car).
It is also shipped with a nice UI (openapi-ui) so that no external REST/HTTP client is required.

![openapi-ui screenshot](assets/OpenApiUI.png)

This webservice contains 
* microstream
* microprofile (config)
* helidon (mp)
  * native-image (experimental)
  * jlink-image (experimental)
* logging via slf4j and log4j
* openapi
* openapi-ui

This project was create for the [Microstream hackathon](https://hackathon.microstream.one/)

## Download
There are prebuilt executables, which save you from building the executable locally.

### JAR
* Check if you have Java 11 installed, if not [install it](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)
* Download the latest/development zip from the checkbuild workflow]
* Unzip it and run it locally with ``java -jar fuel-filling-service.jar``
* Open http://localhost:8080 
→ you should get redirected to the OpenAPI UI


## Building / Executing it locally
If you don't want to use the prebuilt executables you can also build the project.
You can either build and execute this project with Java or Docker.

### Java/JAR
Requirements:
* Java 11
* Maven 3

#### Building the JAR 
* Build the project with ``mvn clean package``
* Run the built jar with ``java -jar target/fuel-filling-service.jar``
* Open http://localhost:8080 

### Docker
Requirements:
* Docker

#### "Normal" Dockerfile
* Build the image with ``docker build -t fuel-filling .``
* Execute it with ``docker run --rm -p 8080:8080 --name fuel-filling fuel-filling``
* Stop/Remove it with ``docker stop fuel-filling``

#### Native Image (experimental)
* Build the native image with ``docker build -f Dockerfile.native -t fuel-filling-native .``

#### JLink Image (experimental)
* Build the native image with ``docker build -f Dockerfile.jlink -t fuel-filling-jlink .``

