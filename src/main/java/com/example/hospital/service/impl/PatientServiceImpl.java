package com.example.hospital.service.impl;

import com.example.hospital.dto.patient.PatientRequestDTO;
import com.example.hospital.dto.patient.PatientResponseDTO;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.DuplicateResourceException;
import com.example.hospital.exception.ResourceNotFoundException;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of PatientService.
 * Handles CRUD operations for Patient entity.
 */
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Create a new patient. Checks for duplicate email.
     */
    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {
        // Check if email already exists
        if (patientRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Patient with email '" + requestDTO.getEmail() + "' already exists");
        }

        Patient patient = mapToEntity(requestDTO);
        Patient savedPatient = patientRepository.save(patient);
        return mapToResponseDTO(savedPatient);
    }

    /**
     * Get all patients.
     */
    @Override
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get patient by ID. Throws ResourceNotFoundException if not found.
     */
    @Override
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return mapToResponseDTO(patient);
    }

    /**
     * Update patient by ID. Checks for duplicate email if email is changed.
     */
    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        // Check email uniqueness if email is being changed
        if (!patient.getEmail().equals(requestDTO.getEmail())) {
            if (patientRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Patient with email '" + requestDTO.getEmail() + "' already exists");
            }
        }

        patient.setFirstName(requestDTO.getFirstName());
        patient.setLastName(requestDTO.getLastName());
        patient.setEmail(requestDTO.getEmail());
        patient.setPhoneNumber(requestDTO.getPhoneNumber());
        patient.setDateOfBirth(requestDTO.getDateOfBirth());
        patient.setGender(requestDTO.getGender());

        Patient updatedPatient = patientRepository.save(patient);
        return mapToResponseDTO(updatedPatient);
    }

    /**
     * Delete patient by ID.
     */
    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    // ===== Helper Methods =====

    private Patient mapToEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        return patient;
    }

    private PatientResponseDTO mapToResponseDTO(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNumber(patient.getPhoneNumber());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        return dto;
    }
}
