FROM openjdk:17
ADD target/flighttracker-1.0-SNAPSHOT.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","app.jar"]