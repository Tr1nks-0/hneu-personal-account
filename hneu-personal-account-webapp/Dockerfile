FROM openjdk:8-jdk-alpine
ADD build/libs/hneu-personal-account-webapp-1.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Dspring.profiles.active=init"
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]