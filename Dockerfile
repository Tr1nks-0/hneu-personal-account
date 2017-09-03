FROM openjdk:8-jdk-alpine
ADD build/libs/hneu-personal-account-1.0-SNAPSHOT.jar app.jar
ADD run-prod-wrapper.sh wrapper.sh
RUN chmod 755 /wrapper.sh
ENTRYPOINT ["/wrapper.sh"]