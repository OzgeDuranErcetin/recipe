FROM openjdk:17-ea-jdk-oracle
ARG JAR_FILE=target/recipe-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} recipe.jar
ENTRYPOINT ["java", "-jar", "recipe.jar"]