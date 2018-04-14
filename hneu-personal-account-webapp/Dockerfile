FROM openjdk:8-jdk-alpine
ADD build/libs/hneu-personal-account-webapp-1.0-SNAPSHOT.jar app.jar
CMD [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]