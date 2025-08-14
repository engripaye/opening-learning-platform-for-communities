FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/learnaccess-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]