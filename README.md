# mars-rover

## How to use
To launch the main application the following command must be executed from main project folder:
```
mvn spring-boot:run -Dspring.profiles.active=local -Dspring-boot.run.arguments="5 5,1 3 N,MMMMMRMM"
```
or, when launching JAR:
```
java -jar mars-rover-1.0-SNAPSHOT.jar --spring.profiles.active=local
```

## Launching web environment
We can simply launch web application without specifying any Spring profile:
```
mvn spring-boot:run
```
Or, when working with a JAR file:
```
java -jar mars-rover-1.0-SNAPSHOT.jar
```

For remote operation we can use CURL to send a POST request with our data. In the example, input data is contained in a plain text file:
```
curl -XPOST -H "Content-Type: text/plain; charset=UTF-8" --data-binary @sample-input.txt http://localhost:8080/rover -w "\n"
```
