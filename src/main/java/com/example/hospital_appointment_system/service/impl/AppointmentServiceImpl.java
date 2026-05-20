package com.example.hospital_appointment_system.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hospital_appointment_system.dto.appointment.AppointmentRequestDTO;
import com.example.hospital_appointment_system.dto.appointment.AppointmentResponseDTO;
import com.example.hospital_appointment_system.dto.doctor.DoctorResponseDTO;
import com.example.hospital_appointment_system.dto.patient.PatientResponseDTO;
import com.example.hospital_appointment_system.entity.Appointment;
import com.example.hospital_appointment_system.entity.AppointmentStatus;
import com.example.hospital_appointment_system.entity.Doctor;
import com.example.hospital_appointment_system.entity.Patient;
import com.example.hospital_appointment_system.exception.AppointmentConflictException;
import com.example.hospital_appointment_system.exception.ResourceNotFoundException;
import com.example.hospital_appointment_system.repository.AppointmentRepository;
import com.example.hospital_appointment_system.repository.DoctorRepository;
import com.example.hospital_appointment_system.repository.PatientRepository;
import com.example.hospital_appointment_system.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO) {

        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + requestDTO.getPatientId()));

        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + requestDTO.getDoctorId()));

        if (!requestDTO.getStartTime().isBefore(requestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        validateNoOverlap(requestDTO.getDoctorId(), requestDTO.getAppointmentDate(), requestDTO, null);

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(requestDTO.getAppointmentDate());
        appointment.setStartTime(requestDTO.getStartTime());
        appointment.setEndTime(requestDTO.getEndTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(requestDTO.getNotes());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapToDTO(savedAppointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        return mapToDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + requestDTO.getPatientId()));

        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + requestDTO.getDoctorId()));

        if (!requestDTO.getStartTime().isBefore(requestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        validateNoOverlap(requestDTO.getDoctorId(), requestDTO.getAppointmentDate(), requestDTO, id);

        appointment.setAppointmentDate(requestDTO.getAppointmentDate());
        appointment.setStartTime(requestDTO.getStartTime());
        appointment.setEndTime(requestDTO.getEndTime());
        appointment.setNotes(requestDTO.getNotes());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return mapToDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        appointmentRepository.delete(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private void validateNoOverlap(Long doctorId, LocalDate date, AppointmentRequestDTO request, Long currentAppointmentId) {
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);

        for (Appointment existing : existingAppointments) {
            if (currentAppointmentId != null && existing.getId().equals(currentAppointmentId)) {
                continue;
            }

            // overlap condition: newStart < existingEnd && newEnd > existingStart
            if (request.getStartTime().isBefore(existing.getEndTime()) && 
                request.getEndTime().isAfter(existing.getStartTime())) {
                throw new AppointmentConflictException("Doctor has an overlapping appointment at this time");
            }
        }
    }

    private AppointmentResponseDTO mapToDTO(Appointment appointment) {
        PatientResponseDTO patientDTO = PatientResponseDTO.builder()
                .id(appointment.getPatient().getId())
                .firstName(appointment.getPatient().getFirstName())
                .lastName(appointment.getPatient().getLastName())
                .email(appointment.getPatient().getEmail())
                .phoneNumber(appointment.getPatient().getPhoneNumber())
                .dateOfBirth(appointment.getPatient().getDateOfBirth())
                .gender(appointment.getPatient().getGender())
                .build();

        DoctorResponseDTO doctorDTO = DoctorResponseDTO.builder()
                .id(appointment.getDoctor().getId())
                .firstName(appointment.getDoctor().getFirstName())
                .lastName(appointment.getDoctor().getLastName())
                .specialization(appointment.getDoctor().getSpecialization())
                .email(appointment.getDoctor().getEmail())
                .phoneNumber(appointment.getDoctor().getPhoneNumber())
                .build();

        return AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .patient(patientDTO)
                .doctor(doctorDTO)
                .build();
    }
}
