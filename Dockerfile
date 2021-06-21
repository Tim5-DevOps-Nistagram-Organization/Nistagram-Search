FROM maven:3.8.1-jdk-11 AS nistagramSearchMicroserviceTest
ARG STAGE=test
WORKDIR /usr/src/server
COPY . .

FROM maven:3.8.1-jdk-11  AS nistagramSearchMicroserviceBuild
ARG STAGE=dev
WORKDIR /usr/src/server
COPY . .
RUN mvn package -Pdev -DskipTests

FROM openjdk:11.0-jdk as nistagramSearchMicroserviceRuntime
COPY --from=nistagramSearchMicroserviceBuild /usr/src/server/target/*.jar nistagram-search.jar
CMD java -jar nistagram-search.jar
