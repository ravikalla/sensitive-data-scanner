# Sensitive Data Scanner

This project is a simple Spring WebFlux application that reads file names from an incoming Kafka topic, downloads the files from Amazon S3, scans them for sensitive data, and publishes the results to an outgoing Kafka topic.

The application is reactive end-to-end using Reactor and integrates with Kafka through `spring-kafka` and `reactor-kafka`.

## Building

You can build the project with Maven or Gradle. Maven is used by default, but a
Gradle build script is also provided.

Using Maven:

```
mvn package
```

Using Gradle:

```
./gradlew build
```

## Running tests

```
mvn test
```

Or with Gradle:

```
./gradlew test
```

The tests include a small Cucumber feature demonstrating the scanning logic.

## Configuration

Configuration values such as Kafka bootstrap servers, topics, and S3 bucket name can be found in `src/main/resources/application.yml`.
