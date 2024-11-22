# Usar uma imagem base do OpenJDK
FROM openjdk:17-jdk as build
# Definir o diretório de trabalho
WORKDIR /demo

# Copiar o JAR do seu projeto para dentro da imagem
COPY target/*.jar /demo/demo-app.jar

# Expor a porta que a aplicação vai rodar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo-app.jar"]

