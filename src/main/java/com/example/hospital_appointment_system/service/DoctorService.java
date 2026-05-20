package com.example.hospital_appointment_system.service;

import java.util.List;

import com.example.hospital_appointment_system.dto.doctor.DoctorRequestDTO;
import com.example.hospital_appointment_system.dto.doctor.DoctorResponseDTO;

public interface DoctorService {

    DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO);

    List<DoctorResponseDTO> getAllDoctors();

    DoctorResponseDTO getDoctorById(Long id);

    DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO);

    void deleteDoctor(Long id);
}
