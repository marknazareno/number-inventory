FROM openjdk:10-slim

EXPOSE 8080

ADD target/number-inventory-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]