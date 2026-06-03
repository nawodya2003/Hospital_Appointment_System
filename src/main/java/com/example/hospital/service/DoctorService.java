package com.example.hospital.service;

import com.example.hospital.dto.doctor.DoctorRequestDTO;
import com.example.hospital.dto.doctor.DoctorResponseDTO;

import java.util.List;

/**
 * Service interface for Doctor operations.
 */
public interface DoctorService {

    DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO);

    List<DoctorResponseDTO> getAllDoctors();

    DoctorResponseDTO getDoctorById(Long id);

    DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO);

    void deleteDoctor(Long id);
}
