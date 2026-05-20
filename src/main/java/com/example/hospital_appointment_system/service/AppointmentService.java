package com.example.hospital_appointment_system.service;

import java.time.LocalDate;
import java.util.List;

import com.example.hospital_appointment_system.dto.appointment.AppointmentRequestDTO;
import com.example.hospital_appointment_system.dto.appointment.AppointmentResponseDTO;

public interface AppointmentService {

    AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO);

    List<AppointmentResponseDTO> getAllAppointments();

    AppointmentResponseDTO getAppointmentById(Long id);

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO);

    void deleteAppointment(Long id);

    List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId);

    List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date);

    List<AppointmentResponseDTO> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date);
}
