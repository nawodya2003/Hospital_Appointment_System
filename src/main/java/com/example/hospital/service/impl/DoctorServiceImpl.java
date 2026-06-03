package com.example.hospital.service.impl;

import com.example.hospital.dto.doctor.DoctorRequestDTO;
import com.example.hospital.dto.doctor.DoctorResponseDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.exception.DuplicateResourceException;
import com.example.hospital.exception.ResourceNotFoundException;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of DoctorService.
 * Handles CRUD operations for Doctor entity.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Create a new doctor. Checks for duplicate email.
     */
    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {
        // Check if email already exists
        if (doctorRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Doctor with email '" + requestDTO.getEmail() + "' already exists");
        }

        Doctor doctor = mapToEntity(requestDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToResponseDTO(savedDoctor);
    }

    /**
     * Get all doctors.
     */
    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get doctor by ID. Throws ResourceNotFoundException if not found.
     */
    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return mapToResponseDTO(doctor);
    }

    /**
     * Update doctor by ID. Checks for duplicate email if email is changed.
     */
    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        // Check email uniqueness if email is being changed
        if (!doctor.getEmail().equals(requestDTO.getEmail())) {
            if (doctorRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Doctor with email '" + requestDTO.getEmail() + "' already exists");
            }
        }

        doctor.setFirstName(requestDTO.getFirstName());
        doctor.setLastName(requestDTO.getLastName());
        doctor.setSpecialization(requestDTO.getSpecialization());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setPhoneNumber(requestDTO.getPhoneNumber());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToResponseDTO(updatedDoctor);
    }

    /**
     * Delete doctor by ID.
     */
    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    // ===== Helper Methods =====

    private Doctor mapToEntity(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setEmail(dto.getEmail());
        doctor.setPhoneNumber(dto.getPhoneNumber());
        return doctor;
    }

    private DoctorResponseDTO mapToResponseDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        return dto;
    }
}
