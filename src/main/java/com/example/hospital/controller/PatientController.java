package com.example.hospital.controller;

import com.example.hospital.dto.patient.PatientRequestDTO;
import com.example.hospital.dto.patient.PatientResponseDTO;
import com.example.hospital.payload.ApiResponse;
import com.example.hospital.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Patient CRUD operations.
 * Base URL: /api/patients
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Create a new patient.
     * POST /api/patients
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(
            @Valid @RequestBody PatientRequestDTO requestDTO) {
        PatientResponseDTO created = patientService.createPatient(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Patient created successfully", created, HttpStatus.CREATED.value()));
    }

    /**
     * Get all patients.
     * GET /api/patients
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(
                ApiResponse.success("Patients retrieved successfully", patients, HttpStatus.OK.value()));
    }

    /**
     * Get patient by ID.
     * GET /api/patients/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> getPatientById(@PathVariable Long id) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Patient retrieved successfully", patient, HttpStatus.OK.value()));
    }

    /**
     * Update patient by ID.
     * PUT /api/patients/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO requestDTO) {
        PatientResponseDTO updated = patientService.updatePatient(id, requestDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Patient updated successfully", updated, HttpStatus.OK.value()));
    }

    /**
     * Delete patient by ID.
     * DELETE /api/patients/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(
                ApiResponse.success("Patient deleted successfully", null, HttpStatus.OK.value()));
    }
}
