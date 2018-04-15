FROM openjdk:8-jdk-alpine
ARG WORKDIR
WORKDIR $WORKDIR
ADD hneu-personal-account-webapp/build/libs/hneu-personal-account-webapp-1.0-SNAPSHOT.jar app.jar
CMD [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]