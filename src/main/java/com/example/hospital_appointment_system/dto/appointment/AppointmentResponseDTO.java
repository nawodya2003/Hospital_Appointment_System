package com.example.hospital_appointment_system.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.hospital_appointment_system.dto.doctor.DoctorResponseDTO;
import com.example.hospital_appointment_system.dto.patient.PatientResponseDTO;
import com.example.hospital_appointment_system.entity.AppointmentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentResponseDTO {

    private Long id;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
    private String notes;
    
    private PatientResponseDTO patient;
    private DoctorResponseDTO doctor;
}
