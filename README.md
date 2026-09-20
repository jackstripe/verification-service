Company Verification Service

A small Spring Boot application for a company verification challenge. It uses the supplied JSON files to simulate free and premium company data providers.

Current progress
Both datasets are loaded from the application resources.
Companies can be searched by identification number, ignoring case and surrounding spaces.
GET /free-third-party?query=CJQ returns matching companies. This provider simulates a 40% chance of being unavailable.
The premium search logic is implemented; its endpoint is next.

The main verification flow, provider fallback, and storage of verification results are still in progress.

Run locally

Requires Java 21. On Windows, from the project directory:

.\mvnw.cmd spring-boot:run

To run the tests:

.\mvnw.cmd test

For example, try http://localhost:8080/free-third-party?query=CJQ. Because availability is simulated, the same request may return 503 Service Unavailable on some attempts.