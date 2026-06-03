package com.example.hospital.service;

import com.example.hospital.dto.appointment.AppointmentRequestDTO;
import com.example.hospital.dto.appointment.AppointmentResponseDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for Appointment operations.
 */
public interface AppointmentService {

    AppointmentResponseDTO bookAppointment(AppointmentRequestDTO requestDTO);

    List<AppointmentResponseDTO> getAllAppointments();

    AppointmentResponseDTO getAppointmentById(Long id);

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO);

    void deleteAppointment(Long id);

    List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId);

    List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date);

    List<AppointmentResponseDTO> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date);
}
