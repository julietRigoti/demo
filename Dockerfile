FROM maven:3.8.1-openjdk-17 as build

WORKDIR /demo

COPY . . 

RUN mvn clean package 


FROM openjdk:17-jdk 

WORKDIR /demo

COPY --from=build /demo/target/demo-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# Rodar a aplicação
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]

