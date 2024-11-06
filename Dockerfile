FROM openjdk:21-jdk
COPY target/demo.jar demo.jar
ENTRYPOINT ["java", "--enable-preview", "-jar", "/demo.jar"]
