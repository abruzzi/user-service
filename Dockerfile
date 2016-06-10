FROM java:8

ADD build/libs/user-service.jar /app/user-service.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
