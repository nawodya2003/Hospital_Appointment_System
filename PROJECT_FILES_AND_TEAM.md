# Hospital Appointment System - Project Files and Team Responsibilities

## Team Members and Assigned Responsibilities

1. **23IT0522 J A N Rashmina**
   - Project Setup and GitHub Management
   - Create Spring Boot project
   - Configure dependencies
   - Create package structure
   - Setup MySQL
   - Create `application.properties`

2. **23IT0480 M R Hanan**
   - Patient entity and repository
   - Create `Patient` entity
   - Create `PatientRepository`

3. **23IT0504 N M Mahuroos**
   - Patient DTO, service, and controller
   - Create `PatientRequestDTO`, `PatientResponseDTO`
   - Create patient service interface/implementation
   - Create patient controller with CRUD APIs

4. **23IT0472 D M S A K Dissanayaka**
   - Doctor entity and repository
   - Create `Doctor` entity
   - Create `DoctorRepository`

5. **23IT0541 W H N Umedya**
   - Doctor DTO, service, and controller
   - Create `DoctorRequestDTO`, `DoctorResponseDTO`
   - Create doctor service interface/implementation
   - Create doctor controller with CRUD APIs

6. **23IT0532 K T S U Senanayaka**
   - Appointment entity and repository
   - Create `Appointment` entity
   - Create `AppointmentStatus` enum
   - Create `AppointmentRepository`

7. **23IT0501 I D N V Lakpura**
   - Appointment DTO, service, and business logic
   - Create `AppointmentRequestDTO`, `AppointmentResponseDTO`
   - Create appointment service interface/implementation
   - Implement booking logic, overlap validation, filter methods

8. **23IT0536 M A C M Silva**
   - Appointment controller and API response
   - Create `AppointmentController`
   - Create standard `ApiResponse` wrapper
   - Support endpoint testing

9. **23IT0519 W A C Ramindu**
   - Validation, exceptions, Postman, documentation
   - Create global exception handler
   - Create custom exceptions
   - Implement validation and testing support
   - Prepare Postman collection and documentation

---

## Project Files

### Root Files

- `.gitignore`
- `.gitattributes`
- `mvnw`
- `mvnw.cmd`
- `pom.xml`
- `README.md`

### Maven Wrapper

- `.mvn/wrapper/maven-wrapper.properties`

### Application Properties

- `src/main/resources/application.properties`

### Java Application Entry Point

- `src/main/java/com/example/hospital/HospitalApplication.java`

### Controllers

- `src/main/java/com/example/hospital/controller/AdminController.java`
- `src/main/java/com/example/hospital/controller/AppointmentController.java`
- `src/main/java/com/example/hospital/controller/DoctorController.java`
- `src/main/java/com/example/hospital/controller/PatientController.java`

### DTOs

- `src/main/java/com/example/hospital/dto/appointment/AppointmentRequestDTO.java`
- `src/main/java/com/example/hospital/dto/appointment/AppointmentResponseDTO.java`
- `src/main/java/com/example/hospital/dto/doctor/DoctorRequestDTO.java`
- `src/main/java/com/example/hospital/dto/doctor/DoctorResponseDTO.java`
- `src/main/java/com/example/hospital/dto/patient/PatientRequestDTO.java`
- `src/main/java/com/example/hospital/dto/patient/PatientResponseDTO.java`

### Entities

- `src/main/java/com/example/hospital/entity/Appointment.java`
- `src/main/java/com/example/hospital/entity/AppointmentStatus.java`
- `src/main/java/com/example/hospital/entity/Doctor.java`
- `src/main/java/com/example/hospital/entity/Patient.java`

### Repositories

- `src/main/java/com/example/hospital/repository/AppointmentRepository.java`
- `src/main/java/com/example/hospital/repository/DoctorRepository.java`
- `src/main/java/com/example/hospital/repository/PatientRepository.java`

### Payload

- `src/main/java/com/example/hospital/payload/ApiResponse.java`

### Services

- `src/main/java/com/example/hospital/service/AppointmentService.java`
- `src/main/java/com/example/hospital/service/DoctorService.java`
- `src/main/java/com/example/hospital/service/DatabaseResetService.java`
- `src/main/java/com/example/hospital/service/PatientService.java`

### Service Implementations

- `src/main/java/com/example/hospital/service/impl/AppointmentServiceImpl.java`
- `src/main/java/com/example/hospital/service/impl/DatabaseResetServiceImpl.java`
- `src/main/java/com/example/hospital/service/impl/DoctorServiceImpl.java`
- `src/main/java/com/example/hospital/service/impl/PatientServiceImpl.java`

### Exceptions

- `src/main/java/com/example/hospital/exception/AppointmentConflictException.java`
- `src/main/java/com/example/hospital/exception/DuplicateResourceException.java`
- `src/main/java/com/example/hospital/exception/GlobalExceptionHandler.java`
- `src/main/java/com/example/hospital/exception/ResourceNotFoundException.java`

---

## Notes

- This file summarizes the source files, configuration files, and the nine-member team assignment list.
- If you want, I can also generate a second markdown file that maps specific files to each team member more directly.
