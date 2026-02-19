# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Production-ready REST API quickstart built with **Java 21**, **Spring Boot 3.4.x**, **Spring Security 6**, **PostgreSQL 16**.
Used as a base template for new backend products at Eicon. The domain example is credit consultation (NFS-e/ISSQN).

Base package: `br.com.eicon.api.consultacredito`

## Build & Run Commands

```bash
# Full build with tests
mvn clean install

# Build skipping tests (used in Docker)
mvn clean package -DskipTests -B

# Run locally with DEV profile (requires PostgreSQL on localhost:5432)
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=DEV"

# Run all tests
mvn test
```

## Docker

```bash
# Development with remote debug on port 5005
docker-compose -f docker/docker-compose-develop.yml up --build

# Production
docker-compose -f docker/docker-compose-prd.yml up --build
```

- `Dockerfile` — multi-stage prod build, no debug agent
- `Dockerfile_local` — dev build with JDWP debug on port 5005
- Copy `.env.example` to `.env` and fill in values before running locally

## Architecture

Layered architecture with interface-based controllers. All layers communicate via well-defined interfaces.

```
api/v1/controller/           → REST interface (@Tag, @Operation) + impl in impl/
api/v1/dto/                  → Request/Response DTOs (Lombok)
api/v1/exceptionhandler/     → Global @ControllerAdvice (ApiErrorHandler + Error)
config/
  security/                  → JWT + API Key filters, SecurityConfig, JwtService, JwtProperties
  OpenApiConfig.java          → SpringDoc OpenAPI 3 (Swagger UI at /swagger-ui.html)
  CorsConfig.java             → Configurable CORS via app.cors.allowed-origins
  DataInitializer.java        → Seeds admin user and API key on first startup
  ExceptionMessageBundleConfig.java → Loads exception.properties (UTF-8)
domain/exception/            → BusinessException + CodigoMensagem enum
domain/service/              → Service interface + impl + ConversorUtil (BeanUtils wrapper)
jpa/entity/                  → JPA entities with @EntityListeners(AuditingEntityListener)
jpa/repository/              → Spring Data JPA repositories
```

## Authentication

Two parallel auth mechanisms, both stateless:

**JWT (for frontends)**
1. `POST /auth/login` with `{ "username": "...", "password": "..." }`
2. Returns `{ "token": "...", "expiresIn": 86400000 }`
3. Use `Authorization: Bearer <token>` on subsequent requests

**API Key (for n8n / machine-to-machine)**
1. Use `X-Api-Key: <key>` header directly — no login step
2. Keys are managed in the `api_key` table
3. On first startup, `DataInitializer` creates the key from `app.init.api-key.value`

Both filters only authenticate if `SecurityContextHolder` is empty. JWT runs before ApiKey in the chain.

Public paths (no auth needed): `/auth/**`, `/actuator/health`, `/v3/api-docs/**`, `/swagger-ui/**`

## Security Config Key Points

- `SecurityConfig` uses `SecurityFilterChain` bean (Spring Security 6 style — no `WebSecurityConfigurerAdapter`)
- `@EnableMethodSecurity` enables `@PreAuthorize` / `@PostAuthorize` on methods
- `FilterRegistrationBean` with `setEnabled(false)` prevents double registration of JWT and ApiKey filters
- `@EnableJpaAuditing` on main class enables `@CreatedDate` / `@LastModifiedDate` on entities
- `JwtProperties` is a Java record bound via `@ConfigurationProperties(prefix = "app.jwt")`

## Adding New Domains (the pattern to follow)

1. Create entity in `jpa/entity/` with `@EntityListeners(AuditingEntityListener.class)` and `createdAt`/`updatedAt`
2. Create repository in `jpa/repository/` extending `JpaRepository`
3. Create service interface in `domain/service/` and implementation in `domain/service/impl/`
4. Throw `BusinessException(CodigoMensagem.XXX)` for business rule violations — `ApiErrorHandler` handles them globally
5. Add error code in `CodigoMensagem` enum and message in `exception.properties`
6. Create controller interface in `api/v1/controller/` with OpenAPI 3 annotations (`@Tag`, `@Operation`, `@ApiResponses`)
7. Create implementation in `api/v1/controller/impl/` with `@RestController` + `@RequestMapping`
8. Use `ConversorUtil.converter(entity, ResponseDto.class)` for entity → DTO conversion

## Exception Handling

- All exceptions flow through `ApiErrorHandler` (`@ControllerAdvice`)
- `BusinessException` → status resolved by `resolveBusinessStatus()` switch (add cases for new domains)
- Error codes prefixed `FG_` (generic framework) or `RN_` (business rules)
- Messages resolved from `exception.properties` (UTF-8) via `ExceptionMessageBundleConfig`

## Environment Variables

All sensitive values are injected via env vars. See `.env.example` for all available variables.

| Variable | Purpose | Required in PROD |
|---|---|---|
| `JWT_SECRET` | Base64-encoded 256-bit key | Yes |
| `DB_URL` / `DB_USER` / `DB_PASSWORD` | Database connection | Yes |
| `ADMIN_USERNAME` / `ADMIN_PASSWORD` | Seed admin user | Yes |
| `API_KEY_VALUE` | Seed API key (e.g. for n8n) | Yes |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed origins | Recommended |

Generate a secure JWT_SECRET: `openssl rand -base64 32`

## Configuration Profiles

| Profile | Port | DB Host | SQL Logging | Notes |
|---|---|---|---|---|
| `DEV` | 8080 | localhost | DEBUG | Default for local dev |
| `HOMOLOG` | 8081 | db | DEBUG | Staging |
| `PROD` | 8080 | ${DB_URL} | Off | All vars mandatory, no defaults |

`spring.jpa.hibernate.ddl-auto=update` is set in all profiles. For production hardening, consider switching to `validate` + a migration tool (Flyway/Liquibase).

## API Endpoints

| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/auth/login` | Public | Returns JWT |
| GET | `/v1/creditos/` | JWT or ApiKey | List all credits |
| GET | `/v1/creditos/{numeroNfse}` | JWT or ApiKey | Credits by NFS-e number |
| GET | `/v1/creditos/credito/{numeroCredito}` | JWT or ApiKey | Single credit (404 if not found) |
| GET | `/actuator/health` | Public | Health check |

Swagger UI: `http://localhost:8080/swagger-ui.html` — supports both Bearer token and X-Api-Key authentication.
