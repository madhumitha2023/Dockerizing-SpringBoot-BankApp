FROM eclipse-temurin:17

LABEL maintainer="madhumitha.krm@gmail.com"

WORKDIR /app

COPY target/banking-app-0.0.1-SNAPSHOT.jar /app/banking-app.jar

ENTRYPOINT ["java", "-jar", "banking-app.jar"]