package com.example.hospital.controller;

import com.example.hospital.dto.doctor.DoctorRequestDTO;
import com.example.hospital.dto.doctor.DoctorResponseDTO;
import com.example.hospital.payload.ApiResponse;
import com.example.hospital.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Doctor CRUD operations.
 * Base URL: /api/doctors
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Create a new doctor.
     * POST /api/doctors
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO) {
        DoctorResponseDTO created = doctorService.createDoctor(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Doctor created successfully", created, HttpStatus.CREATED.value()));
    }

    /**
     * Get all doctors.
     * GET /api/doctors
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getAllDoctors() {
        List<DoctorResponseDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(
                ApiResponse.success("Doctors retrieved successfully", doctors, HttpStatus.OK.value()));
    }

    /**
     * Get doctor by ID.
     * GET /api/doctors/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> getDoctorById(@PathVariable Long id) {
        DoctorResponseDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Doctor retrieved successfully", doctor, HttpStatus.OK.value()));
    }

    /**
     * Update doctor by ID.
     * PUT /api/doctors/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDTO requestDTO) {
        DoctorResponseDTO updated = doctorService.updateDoctor(id, requestDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Doctor updated successfully", updated, HttpStatus.OK.value()));
    }

    /**
     * Delete doctor by ID.
     * DELETE /api/doctors/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(
                ApiResponse.success("Doctor deleted successfully", null, HttpStatus.OK.value()));
    }
}
