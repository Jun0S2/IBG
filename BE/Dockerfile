FROM openjdk:11

ARG JAR_FILE=build/libs/IBG-0.0.1.jar

COPY ${JAR_FILE} app.jar

EXPOSE 7777

ENTRYPOINT ["java", "-jar", "/app.jar"]