# Hospital Appointment System

A Spring Boot REST API for managing doctors, patients, and hospital appointments.

## Overview

This project provides a hospital appointment scheduling system with support for:
- Doctor management (create, list, update, delete)
- Patient management (create, list, update, delete)
- Appointment booking and retrieval
- Appointment lookup by doctor, date, or both
- Standardized API responses and validation

## Technology Stack

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Jakarta Bean Validation
- Lombok

## Getting Started

### Prerequisites

- Java 17 JDK
- Maven (or use bundled `mvnw` / `mvnw.cmd`)
- MySQL database

### Configure the Database

Edit `src/main/resources/application.properties` and set your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=<your-password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Build and Run

From the project root:

```powershell
./mvnw clean package
./mvnw spring-boot:run
```

Or run the generated JAR:

```powershell
java -jar target/hospital-appointment-system-0.0.1-SNAPSHOT.jar
```

The API will start on `http://localhost:8080` by default.

## API Endpoints

Base URL: `http://localhost:8080/api`

### Doctor Endpoints

- `POST /api/doctors` — Create a new doctor
- `GET /api/doctors` — Get all doctors
- `GET /api/doctors/{id}` — Get a doctor by ID
- `PUT /api/doctors/{id}` — Update a doctor by ID
- `DELETE /api/doctors/{id}` — Delete a doctor by ID

### Patient Endpoints

- `POST /api/patients` — Create a new patient
- `GET /api/patients` — Get all patients
- `GET /api/patients/{id}` — Get a patient by ID
- `PUT /api/patients/{id}` — Update a patient by ID
- `DELETE /api/patients/{id}` — Delete a patient by ID

### Appointment Endpoints

- `POST /api/appointments` — Book a new appointment
- `GET /api/appointments` — Get all appointments
- `GET /api/appointments/{id}` — Get an appointment by ID
- `PUT /api/appointments/{id}` — Update an appointment by ID
- `DELETE /api/appointments/{id}` — Delete an appointment by ID
- `GET /api/appointments/doctor/{doctorId}` — Get appointments for a doctor
- `GET /api/appointments/date/{date}` — Get appointments by date (`YYYY-MM-DD`)
- `GET /api/appointments/doctor/{doctorId}/date/{date}` — Get a doctor’s appointments for a specific date
- `POST /api/admin/reset` — Reset all tables and restart IDs at 1

## Response Format

Endpoints return JSON using the shared `ApiResponse` payload for:
- success messages
- request data
- HTTP status codes

## Project Structure

- `controller` — REST endpoints
- `service` — business logic and validation
- `repository` — database access with Spring Data JPA
- `entity` — JPA entities and mapping
- `dto` — request and response payload objects
- `exception` — custom exceptions and global handler

## Notes

- Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`.
- Default server port is `8080`.
- Use proper DTO payloads when creating or updating resources.

## Contact

For questions or enhancements, inspect the controller and service packages in `src/main/java/com/example/hospital`.
