# Company Verification Service

A backend service for verifying companies by their identification number. This project is part of a technical assignment.

The service will consult a free company-data provider first. 
If that provider is unavailable or cannot return a usable result, it will consult a premium provider. 
The backend will return active companies only and store each verification for later retrieval.

## Current status

The project has been initialized with Spring Boot, and both provided company datasets are available under `src/main/resources`. The API endpoints and verification logic are still in development.

## Tech stack

* Java 21
* Spring Boot 4.1.1
* Maven Wrapper
* Spring Web MVC
* Spring Data JPA and H2
* JUnit 5

## Planned endpoints

| Method | Endpoint                                              | Purpose                                                     |
| ------ | ----------------------------------------------------- | ----------------------------------------------------------- |
| GET    | `/free-third-party?query={text}`                      | Search the free dataset by company identification number    |
| GET    | `/premium-third-party?query={text}`                   | Search the premium dataset by company identification number |
| GET    | `/backend-service?verificationId={uuid}&query={text}` | Verify a company, using the premium provider when needed    |
| GET    | `/verifications/{verificationId}`                     | Retrieve a stored verification                              |

The third-party endpoints simulate external providers. Clients will use `/backend-service` to request a verification.

## Run locally

Requires JDK 21. From the project root:

```bash
./mvnw spring-boot:run
```

Run the tests with:

```bash
./mvnw clean test
```

## Implementation plan

1. Load the supplied JSON datasets and expose the simulated provider endpoints.
2. Implement the backend search, active-company filtering, and fallback behavior.
3. Store verification results and make them retrievable by ID.
4. Add tests for successful searches, empty results, provider failures, and persistence.

Implementation details and API response examples will be added as each feature is completed.
