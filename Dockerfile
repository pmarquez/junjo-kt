FROM eclipse-temurin:17-jre-alpine

# copy JAR into image
COPY /junjo-kt-0.9.8-SNAPSHOT.jar /junjo.jar

EXPOSE 8100

ENV application.environment=localhost \
    spring.data.mongodb.database=junjo \
    spring.data.mongodb.uri=mongodb://host.docker.internal:27017 \
    logging.level.org.springframework.data=debug

# run application with this command line
CMD ["java", "-jar", "-Dspring.profiles.active=DEV", "junjo.jar"]
