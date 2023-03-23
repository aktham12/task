FROM openjdk:8-jre
EXPOSE 8086
ADD target/auth-service.jar auth-service.jar
CMD ["java","-jar","auth-service.jar"]

