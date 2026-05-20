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

import com.example.hospital_appointment_system.dto.doctor.DoctorRequestDTO;
import com.example.hospital_appointment_system.dto.doctor.DoctorResponseDTO;
import com.example.hospital_appointment_system.payload.ApiResponse;
import com.example.hospital_appointment_system.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DoctorResponseDTO> createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        DoctorResponseDTO response = doctorService.createDoctor(requestDTO);
        return ApiResponse.<DoctorResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Doctor created successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<DoctorResponseDTO>> getAllDoctors() {

        List<DoctorResponseDTO> response = doctorService.getAllDoctors();
        return ApiResponse.<List<DoctorResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Doctors fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {

        DoctorResponseDTO response = doctorService.getDoctorById(id);
        return ApiResponse.<DoctorResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Doctor fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DoctorResponseDTO> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        DoctorResponseDTO response = doctorService.updateDoctor(id, requestDTO);
        return ApiResponse.<DoctorResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Doctor updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Doctor deleted successfully")
                .data(null)
                .build();
    }
}
