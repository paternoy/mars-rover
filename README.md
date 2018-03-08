# mars-rover
This is a Java exercise, based on the Mars Rover programming problem.

The goal is to provide an application to deploy and instruct Rovers in a rectangular Plateau. The solution must offer two interfaces for operation:
* Command line remote
* Remote operation

## Solution
The solution proposed is based in Java 8 and the Spring framework, making use of the Spring Boot capabilities for dependency management and default configuration.

Command-line usage is activated by using a specific Spring profile called "local", that enables command-line argument parsing and disables web environment via property configuration.

The web part uses Spring MVC for REST endpoint creation and includes an embedded Tomcat server that runs in port 8080.

Both user interfaces share the same logic contained in a common controller.

Tests have been developed with JUnit 4, and include some other tools for specific uses:
* MockMvc, from Spring, to test REST enpoints
* Mockito to mock and spy calls to nested components

## How to use
To launch the main application the following command must be executed from main project folder:
```
mvn spring-boot:run -Dspring.profiles.active=local -Dspring-boot.run.arguments="5 5,1 3 N,MMMMMRMM"
```
or, to launch from a JAR:
```
mvn package
java -jar target/mars-rover-1.0-SNAPSHOT.jar --spring.profiles.active=local "5 5" "1 3 N" "MMMMMRMM"
```

## Launching web environment
We can launch the  web application without specifying any Spring profile:
```
mvn spring-boot:run
```
Or, when working with a JAR file:
```
mvn package
java -jar mars-rover-1.0-SNAPSHOT.jar
```
After launching the process, the server will be ready and waiting for requests.
For remote operation we can use CURL as a client to send a POST request with our data. In the example, input data is contained in a plain text file:
```
curl -XPOST -H "Content-Type: text/plain; charset=UTF-8" --data-binary @sample-input.txt http://localhost:8080/rover -w "\n"
```
