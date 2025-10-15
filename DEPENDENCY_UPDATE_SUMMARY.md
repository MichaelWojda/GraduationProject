# Dependency Update Summary

This document summarizes the dependency updates made to modernize the GraduationProject.

## Major Version Updates

### Spring Boot
- **Before:** 2.4.0 (released December 2020)
- **After:** 3.3.5 (released October 2024)
- **Changes:** Latest stable version with Jakarta EE 9+ support

### Java
- **Before:** Java 15 (non-LTS, end of support)
- **After:** Java 17 (LTS, supported until September 2029)
- **Changes:** Modern LTS version with improved performance and features

### JWT Library
- **Before:** jjwt 0.9.0 (single artifact, deprecated)
- **After:** jjwt-api 0.12.6 with jjwt-impl and jjwt-jackson (modular architecture)
- **Changes:** Modern API with better security and performance

### Database Driver
- **Existing:** PostgreSQL (latest from Spring Boot parent)
- **Added:** H2 Database for testing (test scope only)

## Code Modernization

### Package Migration
- Migrated all `javax.*` imports to `jakarta.*` namespace
  - `javax.persistence.*` → `jakarta.persistence.*`
  - `javax.validation.*` → `jakarta.validation.*`
  - `javax.servlet.*` → `jakarta.servlet.*`
  - `javax.transaction.*` → `org.springframework.transaction.annotation.*`

### Security Configuration
- Removed deprecated `WebSecurityConfigurerAdapter`
- Replaced with modern `SecurityFilterChain` bean approach
- Updated `@EnableGlobalMethodSecurity` to `@EnableMethodSecurity`
- Migrated to lambda-based HttpSecurity configuration DSL
- Added `DaoAuthenticationProvider` bean for proper authentication setup

### Application Properties
- `spring.datasource.tomcat.initial-size` → `spring.datasource.hikari.maximum-pool-size`
- `spring.datasource.initialization-mode` → `spring.sql.init.mode`
- HikariCP is now the default connection pool in Spring Boot 3.x

### JWT Token Service
- Updated to use new JJWT 0.12.x API
- Replaced deprecated `SignatureAlgorithm` with `Jwts.SIG.HS512`
- Updated key handling to use `SecretKey` with proper encoding
- Simplified exception handling with generic `JwtException`
- Changed from `setSubject()` to `subject()` (builder pattern)
- Changed from `parseClaimsJws()` to `parseSignedClaims()`

## Test Infrastructure

### New Test Configuration
- Created `application-test.properties` for test profile
- Added H2 in-memory database for testing (no external database needed)
- Updated test class to use `@ActiveProfiles("test")`
- Configured proper test isolation with create-drop DDL strategy

## Build and Test Results

✅ All compilation successful with Java 17
✅ All tests passing (1/1)
✅ Package build successful
✅ CodeQL security scan completed (CSRF disablement documented as intentional)

## Breaking Changes Handled

1. **WebSecurityConfigurerAdapter removal** - Refactored to SecurityFilterChain
2. **antMatchers() deprecated** - Updated to requestMatchers()
3. **Method chaining changes** - Updated to lambda DSL for HttpSecurity
4. **JJWT API changes** - Updated to new builder pattern and method names
5. **Jakarta namespace** - Migrated all javax imports

## Benefits

- **Security:** Latest security patches and improvements
- **Performance:** Better performance with Java 17 and Spring Boot 3.3
- **Maintainability:** Modern APIs and patterns
- **Long-term Support:** Java 17 LTS and Spring Boot 3.x active maintenance
- **Testing:** Proper test infrastructure with H2 database

All changes are backward compatible in terms of functionality - the application behavior remains the same.
