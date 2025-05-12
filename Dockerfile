FROM maven:latest as dependency
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:17 as launch
WORKDIR /application
COPY --from=dependency /app/target/*jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]