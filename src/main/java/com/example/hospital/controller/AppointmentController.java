package com.example.hospital.controller;

import com.example.hospital.dto.appointment.AppointmentRequestDTO;
import com.example.hospital.dto.appointment.AppointmentResponseDTO;
import com.example.hospital.payload.ApiResponse;
import com.example.hospital.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Appointment operations.
 * Base URL: /api/appointments
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Book a new appointment.
     * POST /api/appointments
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> bookAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {
        AppointmentResponseDTO booked = appointmentService.bookAppointment(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Appointment booked successfully", booked, HttpStatus.CREATED.value()));
    }

    /**
     * Get all appointments.
     * GET /api/appointments
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAllAppointments() {
        List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved successfully", appointments, HttpStatus.OK.value()));
    }

    /**
     * Get appointment by ID.
     * GET /api/appointments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Appointment retrieved successfully", appointment, HttpStatus.OK.value()));
    }

    /**
     * Update appointment by ID.
     * PUT /api/appointments/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {
        AppointmentResponseDTO updated = appointmentService.updateAppointment(id, requestDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Appointment updated successfully", updated, HttpStatus.OK.value()));
    }

    /**
     * Delete appointment by ID.
     * DELETE /api/appointments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(
                ApiResponse.success("Appointment deleted successfully", null, HttpStatus.OK.value()));
    }

    /**
     * Get appointments by doctor ID.
     * GET /api/appointments/doctor/{doctorId}
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(
                ApiResponse.success("Doctor's appointments retrieved successfully", appointments, HttpStatus.OK.value()));
    }

    /**
     * Get appointments by date.
     * GET /api/appointments/date/{date}
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDate(date);
        return ResponseEntity.ok(
                ApiResponse.success("Appointments for date retrieved successfully", appointments, HttpStatus.OK.value()));
    }

    /**
     * Get appointments by doctor ID and date.
     * GET /api/appointments/doctor/{doctorId}/date/{date}
     */
    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDoctorAndDate(
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctorAndDate(doctorId, date);
        return ResponseEntity.ok(
                ApiResponse.success("Doctor's appointments for date retrieved successfully", appointments, HttpStatus.OK.value()));
    }
}
