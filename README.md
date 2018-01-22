# Stock Service Application
Application to manage stocks based on **Spring Boot Rest Api** on backend and **React** on frontend.
Provides basic CRUD operations on stocks via Rest Api and simple web ui to display all stocks.

## Technology choices
**Backend:**   
* Lightweight **Spring Boot** service with embedded tomcat and Rest Api secured by **JWT tokens**.
* Rest Api is documented with **Swagger and SpringFox**, which provides nice UI with all documentation and ability to test services
* Persistence based on **Spring Data JPA** and **in-memory h2 database** to simplify the deployment and avoid external dependencies. (not suitable for production, but easily replaceable by other relational database) 
* Unit and integration testing with **JUnit and Mockito**. Rest Api integration testing with spring-test.

**Frontend:**
* Web UI based on **React framework** with react-bootstrap components
* Web resources are packaged inside the backend application artifact to simplify deployment and testing:
  * _**frontend-maven-plugin**_ is used to install node and npm locally during maven build and resolve dependencies;
  * _**webpack**_ is used to bundle all javascript dependencies in one file for packaging inside fat jar;
* Normally frontend application should be deployed independently, as you may have multiple frontend apps(mobile app, web UI etc.),
but in this case it was intentionally mixed together to reduce the scope and simplify testing. 

## Prerequisites
To be able to run application you need to have installed:
- JDK8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Maven 3+ (https://maven.apache.org/download.cgi)

## Running modes
By default application will run in **development mode** (see application.properties): 
- Logs are printed only to console and root level for application packages - DEBUG
- Application uses in-memory database

To run in **production mode**, application should be started with spring profile "server" activated (see application-server.properties):
- Logs will be printed to file in the logs folder of the root directory
- Logging root level for all packages - INFO

## How to run application
**With maven:**    
Use spring boot plugin to start(it will build backend and frontend):   
_mvn spring-boot:run_

**For deployment on test/production server:**   
Build jar package with maven:   
 _mvn clean package_  
Run application with:  
 _java -jar stock-service-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=server_
   
## Predefined dataset  
On startup in-memory database will be populated with five different stocks and random prices:

| Id   | Name  |
|:----:|:-----:|
| 1    | ABN   |
| 2    | ING   |
| 3    | RABO  |
| 4    | SNS   |
| 5    | BUNQ  |

## Testing of application functionality    
Rest API is protected by JWT(https://jwt.io/), which means that to be able to access API you have to obtain JWT token first.   
You can do it by sending POST request with username/password to the token endpoint:  **http://localhost:5000/token**.    
For simplicity there is only one user defined with credentials: **admin/password**    
Example of request to get token:  

**curl -i -X POST "http://localhost:5000/token" -H "accept: */*" -H "Content-Type: application/json;charset=UTF-8" -d "{ \"username\": \"admin\", \"password\": \"password\"}"**

Response header "Authorization" will contain your token.

Once you have the token you can use it to send requests to API by providing it as request header "Authorization".    
It should be provided in format "Bearer %token". E.g. your request to get all stocks may look like this:   

**curl -X GET "http://localhost:5000/api/v1/stocks" -H "accept: application/json;charset=UTF-8" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTUxNjU2ODE5MywiZXhwIjoxNTE2NTcxNzkzfQ.1049OyvsPWhGN0spV4ME5hg9XUQey040oIlGSKIu3Q8"**

Or you can use Swagger UI, which contains Rest Api documentation:     
**_http://localhost:5000/swagger-ui.html_**  

Also you can visit Web UI through browser to see all the available stocks:   
**_http://localhost:5000/ui_**

## Limitations
- Application uses in-memory relational database h2, so after restart all the data is reloaded.
- On startup application creates tables in the database based on JPA-entities, which is not suitable for production.
- On frontend part user credentials are hardcoded and used to obtain JWT token to use it for consuming REST API
- Web UI is very basic and is mostly done for technology demonstration purposes

## TODO (for the next iterations)
- Move to proper persistence storage: relational or NoSql database.
- Use encrypted transport protocol(TLS) for Rest Api.
- Improve authentication/authorization with proper user/roles management.
- Add support for encrypted properties to avoid sensitive data provided in plain text.
- Extract Web UI part as an independent frontend application and add more features.
- Configure CI/CD server for build and deployment
- Improve input validation for Rest Api. Define better error messages in property file. 
- Add pagination for GET all stocks api to be able to limit amount of returned items.
- Add separate maven profile to run integration tests.
