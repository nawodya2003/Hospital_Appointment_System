package com.example.hospital_appointment_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_appointment_system.dto.patient.PatientRequestDTO;
import com.example.hospital_appointment_system.dto.patient.PatientResponseDTO;
import com.example.hospital_appointment_system.payload.ApiResponse;
import com.example.hospital_appointment_system.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO requestDTO) {

        PatientResponseDTO response = patientService.createPatient(requestDTO);
        return ApiResponse.<PatientResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Patient created successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<PatientResponseDTO>> getAllPatients() {

        List<PatientResponseDTO> response = patientService.getAllPatients();
        return ApiResponse.<List<PatientResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Patients fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PatientResponseDTO> getPatientById(@PathVariable Long id) {

        PatientResponseDTO response = patientService.getPatientById(id);
        return ApiResponse.<PatientResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Patient fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO requestDTO) {

        PatientResponseDTO response = patientService.updatePatient(id, requestDTO);
        return ApiResponse.<PatientResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Patient updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Patient deleted successfully")
                .data(null)
                .build();
    }
}