# **TaskControl API 1.0**
## Task Managment system

## Features.

* Project developed in Java version 8 Open JDK 1.8.
* Maven version 3.5.4.
* Implemented documentation using Swagger.
* SpringBoot security with single login with default credentials as administrator (Step 2).
* H2 database.
* To expedite the testing process, the project is automatically preloaded with 20 records upon execution.
* Unit testing with JUnit.


## Execution Steps.

1. Run SpringBoot application with command: `mvn spring-boot:run`
2. Go to http://localhost:8080/swagger-ui.html in your browser to view the Swagger UI documentation.
3. Login with the following credentials: **Username:** _admin_ **Password:** _123_.
4. Now you can access any REST endpoint to retrieve the response.
5. Finally, run the tests with command: `mvn test`