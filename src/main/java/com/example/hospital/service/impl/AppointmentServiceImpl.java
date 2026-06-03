package com.example.hospital.service.impl;

import com.example.hospital.dto.appointment.AppointmentRequestDTO;
import com.example.hospital.dto.appointment.AppointmentResponseDTO;
import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.AppointmentStatus;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.AppointmentConflictException;
import com.example.hospital.exception.ResourceNotFoundException;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of AppointmentService.
 * Handles appointment booking, overlap validation, and filtering.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Book a new appointment.
     * Validates patient, doctor, time, and checks for overlapping appointments.
     */
    @Override
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO) {
        // 1. Validate patient exists
        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + requestDTO.getPatientId()));

        // 2. Validate doctor exists
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + requestDTO.getDoctorId()));

        // 3. Validate startTime is before endTime
        if (!requestDTO.getStartTime().isBefore(requestDTO.getEndTime())) {
            throw new AppointmentConflictException(
                    "Start time must be before end time");
        }

        // 4. Check for overlapping appointments for the same doctor on the same date
        checkForOverlappingAppointments(
                requestDTO.getDoctorId(),
                requestDTO.getAppointmentDate(),
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                null // null means we are creating a new appointment
        );

        // 5. Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(requestDTO.getAppointmentDate());
        appointment.setStartTime(requestDTO.getStartTime());
        appointment.setEndTime(requestDTO.getEndTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(requestDTO.getNotes());

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDTO(savedAppointment);
    }

    /**
     * Get all appointments.
     */
    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get appointment by ID.
     */
    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));
        return mapToResponseDTO(appointment);
    }

    /**
     * Update an existing appointment.
     * Re-validates patient, doctor, time, and overlap.
     */
    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));

        // Validate patient exists
        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + requestDTO.getPatientId()));

        // Validate doctor exists
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + requestDTO.getDoctorId()));

        // Validate startTime is before endTime
        if (!requestDTO.getStartTime().isBefore(requestDTO.getEndTime())) {
            throw new AppointmentConflictException(
                    "Start time must be before end time");
        }

        // Check overlap, excluding the current appointment being updated
        checkForOverlappingAppointments(
                requestDTO.getDoctorId(),
                requestDTO.getAppointmentDate(),
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                id // exclude this appointment from overlap check
        );

        // Update the appointment
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(requestDTO.getAppointmentDate());
        appointment.setStartTime(requestDTO.getStartTime());
        appointment.setEndTime(requestDTO.getEndTime());
        appointment.setNotes(requestDTO.getNotes());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDTO(updatedAppointment);
    }

    /**
     * Delete appointment by ID.
     */
    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    /**
     * Filter appointments by doctor ID.
     */
    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filter appointments by date.
     */
    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filter appointments by doctor and date.
     */
    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ===== Private Helper Methods =====

    /**
     * Checks if the new appointment time overlaps with existing appointments
     * for the same doctor on the same date.
     *
     * Overlap Rule: newStart < existingEnd AND newEnd > existingStart
     *
     * @param doctorId        the doctor's ID
     * @param date            the appointment date
     * @param newStart        the proposed start time
     * @param newEnd          the proposed end time
     * @param excludeId       the appointment ID to exclude (null for new appointments)
     */
    private void checkForOverlappingAppointments(Long doctorId, LocalDate date,
                                                  java.time.LocalTime newStart,
                                                  java.time.LocalTime newEnd,
                                                  Long excludeId) {
        List<Appointment> existingAppointments =
                appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);

        for (Appointment existing : existingAppointments) {
            // Skip the appointment being updated
            if (excludeId != null && existing.getId().equals(excludeId)) {
                continue;
            }

            // Check overlap: newStart < existingEnd AND newEnd > existingStart
            if (newStart.isBefore(existing.getEndTime()) && newEnd.isAfter(existing.getStartTime())) {
                throw new AppointmentConflictException(
                        "Doctor already has an appointment from " + existing.getStartTime()
                                + " to " + existing.getEndTime()
                                + " on " + date
                                + ". The requested time (" + newStart + " - " + newEnd + ") overlaps.");
            }
        }
    }

    /**
     * Maps Appointment entity to AppointmentResponseDTO.
     */
    private AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus());
        dto.setNotes(appointment.getNotes());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
        return dto;
    }
}
