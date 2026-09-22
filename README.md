Company Verification Service

A small Spring Boot application created for a backend technical challenge. It simulates two external company providers and exposes a service that searches them using a fallback strategy.

How it works

The backend calls the FREE provider first. If it returns no results or responds with 503 Service Unavailable, the request falls back to the PREMIUM provider.

Only active companies are included in the final response. The first match is returned in result and any additional matches are included in otherResults.

Every verification is stored in an in-memory H2 database and can be retrieved using its verification ID.

Endpoints
GET /free-third-party?query={text}
GET /premium-third-party?query={text}
GET /backend-service?verificationId={uuid}&query={text}
GET /verifications/{verificationId}

The FREE provider simulates a 40% failure rate and the PREMIUM provider simulates a 10% failure rate.

Run locally

Java 21 is required.

.\mvnw.cmd spring-boot:run

Run the tests with:

.\mvnw.cmd clean test

Example:

GET /backend-service?verificationId=550e8400-e29b-41d4-a716-446655440000&query=CJQ

The H2 database is stored in memory, so saved verifications are cleared when the application stops.

To retrieve verifications:

GET /verifications/550e8400-e29b-41d4-a716-446655440000