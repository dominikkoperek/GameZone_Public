FROM maven:3.9.4-amazoncorretto-21-debian-bookworm AS MAVEN_BUILD
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
COPY . /
RUN mvn clean package




FROM openjdk:21-slim-bookworm
EXPOSE 8080
COPY --from=MAVEN_BUILD /target/gameZone-project-*.jar /app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]