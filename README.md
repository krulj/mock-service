# simple-service
Simple service written in Java using Spring Boot. 

Exposes three API endpoints:
1. Primary Third Party API Endpoint (FREE service)
2. Secondary Third Party API Endpoint (PREMIUM service)
3. Backend Service Endpoint (the main API that handles client requests)

## Prerequisites

- Java 17+
- Docker (for Testcontainers or Postgres instance)
- Maven

## Getting started

### Option 1 - Manualy run app

Start a PostgreSQL instance:
- ```docker run --name incode-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=incode -p 5432:5432 -d postgres```
- Locally installed Postgres with database incode can be also used

Run:
- ```mvn clean install```
- ```mvn spring-boot:run```

### Option 2 - Run dockerized app and postgres

- ```mvn clean install```
- ```docker-compose up --build```

### Access application 

The endpoints will be available at:
- ```http://localhost:9876/v1/third-party/free-third-party?query=```
- ```http://localhost:9876/v1/third-party/premium-third-party?query=```
- ```http://localhost:9876/v1/api/backend-service?query={{query_string}}&verificationId={{$guid}}```
- ```http://localhost:9876/v1/verification/{{$guid}}```

Swagger documentation at
-  ```http://localhost:9876/swagger-ui/index.html#```

## General notes about implementation
1. **Primary and secondary third-party api endpoints**
    - in real world scenario should be implemented in separate projects
    - for simplicity of solution, they are included within the same project
    - to maintain clean and modular code, all related logic is organized in separate packages
    - ```com.incode.simpleservice.api``` – main api handling client requests
    - ```com.incode.simpleservice.third_party``` – simulated third-party services
    - each package does not import logic from the other
    - single sql instance is used for simplicity of configuration

3. **Use of RestTemplate to simulate network requests**
   - failure simulation at the controller level 

4. **Different result objects in api response**
   - per assignment requirements
   - company is found - returns a ```CompanyMatchDTO```
   - multiple companies match - includes an ```otherResults``` array
   - no results or services are down - returns an informational message

5. **Storing json as matching result in verification table**
   - flexible result storage, no schema changes are required on change
   - easier support for different verification response formats in future updates

6. **Sql vs noSql for verification data**
   - sql was chosen in this project for simplicity and ease of use
   - in a real production environment, a nosql database might be considered if:
   - frequent schema changes are expected
   - high scalability is required for very large datasets
   - schema migrations need to be avoided in case verificationid format changes
