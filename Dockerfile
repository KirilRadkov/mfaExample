FROM openjdk:21-slim-buster
COPY target/MFAService-0.0.1-SNAPSHOT.jar mfa-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djasypt.encryptor.password=mfanexo","-jar","mfa-service.jar"]