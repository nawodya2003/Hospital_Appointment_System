package com.example.hospital_appointment_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_appointment_system.dto.appointment.AppointmentRequestDTO;
import com.example.hospital_appointment_system.dto.appointment.AppointmentResponseDTO;
import com.example.hospital_appointment_system.payload.ApiResponse;
import com.example.hospital_appointment_system.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {

        AppointmentResponseDTO response = appointmentService.createAppointment(requestDTO);
        return ApiResponse.<AppointmentResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Appointment booked successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponseDTO>> getAllAppointments() {

        List<AppointmentResponseDTO> response = appointmentService.getAllAppointments();
        return ApiResponse.<List<AppointmentResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Appointments fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {

        AppointmentResponseDTO response = appointmentService.getAppointmentById(id);
        return ApiResponse.<AppointmentResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Appointment fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AppointmentResponseDTO> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {

        AppointmentResponseDTO response = appointmentService.updateAppointment(id, requestDTO);
        return ApiResponse.<AppointmentResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Appointment updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Appointment deleted successfully")
                .data(null)
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<AppointmentResponseDTO>> getAppointmentsByDoctor(@PathVariable Long doctorId) {

        List<AppointmentResponseDTO> response = appointmentService.getAppointmentsByDoctor(doctorId);
        return ApiResponse.<List<AppointmentResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Appointments fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/date/{date}")
    public ApiResponse<List<AppointmentResponseDTO>> getAppointmentsByDate(@PathVariable LocalDate date) {

        List<AppointmentResponseDTO> response = appointmentService.getAppointmentsByDate(date);
        return ApiResponse.<List<AppointmentResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Appointments fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ApiResponse<List<AppointmentResponseDTO>> getAppointmentsByDoctorAndDate(
            @PathVariable Long doctorId,
            @PathVariable LocalDate date) {

        List<AppointmentResponseDTO> response = appointmentService.getAppointmentsByDoctorAndDate(doctorId, date);
        return ApiResponse.<List<AppointmentResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Appointments fetched successfully")
                .data(response)
                .build();
    }
}
