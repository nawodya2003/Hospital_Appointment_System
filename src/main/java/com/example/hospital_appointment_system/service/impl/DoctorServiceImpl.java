package com.example.hospital_appointment_system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hospital_appointment_system.dto.doctor.DoctorRequestDTO;
import com.example.hospital_appointment_system.dto.doctor.DoctorResponseDTO;
import com.example.hospital_appointment_system.entity.Doctor;
import com.example.hospital_appointment_system.exception.DuplicateResourceException;
import com.example.hospital_appointment_system.exception.ResourceNotFoundException;
import com.example.hospital_appointment_system.repository.DoctorRepository;
import com.example.hospital_appointment_system.service.DoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {

        if (doctorRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Doctor with email " + requestDTO.getEmail() + " already exists");
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(requestDTO.getFirstName());
        doctor.setLastName(requestDTO.getLastName());
        doctor.setSpecialization(requestDTO.getSpecialization());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setPhoneNumber(requestDTO.getPhoneNumber());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToDTO(savedDoctor);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        return mapToDTO(doctor);
    }

    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        if (!doctor.getEmail().equals(requestDTO.getEmail()) && 
            doctorRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Doctor with email " + requestDTO.getEmail() + " already exists");
        }

        doctor.setFirstName(requestDTO.getFirstName());
        doctor.setLastName(requestDTO.getLastName());
        doctor.setSpecialization(requestDTO.getSpecialization());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setPhoneNumber(requestDTO.getPhoneNumber());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return mapToDTO(updatedDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
    }

    private DoctorResponseDTO mapToDTO(Doctor doctor) {
        return DoctorResponseDTO.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialization(doctor.getSpecialization())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .build();
    }
}
