FROM openjdk:17-alpine

WORKDIR /app

ADD domain-ws/target/Application-server-ws.jar app.jar

EXPOSE 8080

CMD ["java","-jar","/app/app.jar"]