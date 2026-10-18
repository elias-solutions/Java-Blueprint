Java Backend Blueprint (port of `NET-Blueprint`):
- Java 21, Spring Boot 4.1
- CQRS (command/query handlers), Spring Data JPA on PostgreSQL, schema via Flyway (`db/migration`)
- Versioned REST API (`/api/v1`, `/api/v2`), Swagger UI (springdoc)
- Role-based security, ProblemDetail error responses, optimistic locking via entity versions, fixed-window rate limiting

Run
- start PostgreSQL from the Blueprint root: `docker compose up -d` (database `blueprint_java`)
- `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`)
- Swagger UI: http://localhost:5105/swagger (v1 / v2 in the definition dropdown)

Test
- `./mvnw test` runs all tests (integration tests + unit tests)
- `./mvnw test -Dgroups=integration` runs only the integration tests
- Integration tests boot the full app on a random port with a mocked `UserService` and a Testcontainers PostgreSQL (see `environment/IntegrationTest`; Docker required)

Docker
- the image wraps the jar from `target/` (`./mvnw package`), see `Dockerfile`
- from the Blueprint root: `./build-images.sh` (build missing images, migrate, compose up) or `./build-images.sh -f` (force rebuild)
- `docker compose up -d` starts PostgreSQL + all APIs (Java Api: http://localhost:5105/swagger)
