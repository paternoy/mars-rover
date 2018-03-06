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
´´´
java -jar mars-rover-1.0-SNAPSHOT.jar
´´´
´´´
mvn spring-boot:run
´´´

For remote operation we can use CURL to send a POST request with our data. In the example, input data is contained in a plain text file:
´´´
curl -XPOST -H "Content-Type: text/plain; charset=UTF-8" --data-binary @sample-input.txt http://localhost:8080/rover -w "\n"
´´´
