# Stock Service Application
Backend application to manage stocks

## Prerequisites
To be able to run application you need to have installed:
- JDK8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Maven 3+ (https://maven.apache.org/download.cgi)

## Important configuration files
application.properties - default configuration of application (used in dev mode)
application-server.properties - test/production configuration
spring-logback.xml - logging configuration

## Running modes
By default application will run in **development mode** (see application.properties): 
- Logs are printed only to console and root level for application packages - DEBUG
- Application uses in-memory database

To run in **production mode**, application should be started with spring profile "server" activated (see application-server.properties):
- Logs will be printed to file in the logs folder in the root directory
- Logging root level for all packages - INFO

## How to run application
**With any java IDE:**
Run main class - StockServiceApplication.java

**With maven:**
Execute command in the root folder of the application to start it:
mvn spring-boot:run

**For deployment on test/production server:**   
Build jar package with maven:   
 mvn clean package  
Run application with:  
 java -jar stock-service-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=server

## Application URLs
- Swagger Rest Api documentation:
  http://localhost:5000/swagger-ui.html  
    
## Testing of application functionality    
 On startup in-memory database will be populated with five different stocks and random prices:

| Id   | Name  |
|:----:|:-----:|
| 1    | ABN   |
| 2    | ING   |
| 3    | RABO  |
| 4    | SNS   |
| 5    | BUNQ  |

## Limitations
- Application uses in-memory relational database h2, so after restart all the data is reloaded.
- On startup application creates tables in the database based on JPA-entities, which is not suitable for production

## TODO (for the next iterations)
- Move to proper persistence storage: relational or NoSql database.
- Use encrypted transport protocol(TLS) for REST API.
- Improve input validation for REST API. Define better error messages in property file. 
- Add separate maven profile to run integration tests
