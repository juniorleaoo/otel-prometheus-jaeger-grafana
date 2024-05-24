FROM openjdk:21-jdk

COPY ./app/user-11/build/libs/user-0.0.1.jar app.jar
COPY ./opentelemetry/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

ENTRYPOINT ["sh", "-c", "java -javaagent:opentelemetry-javaagent.jar -jar app.jar"]