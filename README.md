# Stock Service Application
Backend application to manage stocks

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
**With any java IDE:**      
Run main class - _StockServiceApplication.java_

**With maven:**    
Use spring boot plugin to start it:   
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

## Limitations
- Application uses in-memory relational database h2, so after restart all the data is reloaded.
- On startup application creates tables in the database based on JPA-entities, which is not suitable for production

## TODO (for the next iterations)
- Move to proper persistence storage: relational or NoSql database.
- Use encrypted transport protocol(TLS) for REST API.
- Improve authentication/authorization with proper user/roles management
- Add supported for encrypted properties to avoid sensitive data provided in plain text
- Improve input validation for REST API. Define better error messages in property file. 
- Add separate maven profile to run integration tests
