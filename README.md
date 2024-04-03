# Multi-Factor Authentication Service (MFAService)

### Summary

This service provides an REST-API for creation and verification of Verification-Codes for Multi-Factor-Authentication via Email

### How to build and run the service
To build this Maven project via the command line, you need to use the **mvn** command.The command must be executed in the directory which contains the relevant pom file

```
mvn clean install 
```

To build the docker image use following command:

```
docker-compose build 
```

To run the docker container and the MYSQL-Database use following command:

```
docker-compose up --force-recreate
```

### How to test the service 

1. Start the service and the Database with:

```
docker-compose up --force-recreate
```
2. Open your browser and go to http://localhost:8081/swagger-ui/index.html#/
3. To generate a Verification code for specific Email address use the **"/api/rest/code"**-Endpoint (use the "Try it out"-Button) 
   and use the following JSON as Request body:
```json
{
  "email": "yourEmailAddress@mail.com"
}
```
4. Check your Email inbox and copy the Verification code
5. To verify the Verification code you can use the **"/api/rest/verification"**-Endpoint (use the "Try it out"-Button and use 
    the following JSON as Request body:
```json
{
  "email": "yourEmailAddress@mail.com",
  "verificationCode": "copiedCode"
}
```


