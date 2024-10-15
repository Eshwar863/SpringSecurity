FROM openjdk:21-jdk
COPY target/demo.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/demo.jar"]