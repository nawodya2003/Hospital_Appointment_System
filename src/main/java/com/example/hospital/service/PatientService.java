package com.example.hospital.service;

import com.example.hospital.dto.patient.PatientRequestDTO;
import com.example.hospital.dto.patient.PatientResponseDTO;

import java.util.List;

/**
 * Service interface for Patient operations.
 */
public interface PatientService {

    PatientResponseDTO createPatient(PatientRequestDTO requestDTO);

    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO getPatientById(Long id);

    PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO);

    void deletePatient(Long id);
}
