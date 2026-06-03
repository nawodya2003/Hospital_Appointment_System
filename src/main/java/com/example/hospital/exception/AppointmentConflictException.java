package com.example.hospital.exception;

/**
 * Thrown when an appointment overlaps with an existing appointment for the same doctor.
 */
public class AppointmentConflictException extends RuntimeException {

    public AppointmentConflictException(String message) {
        super(message);
    }
}
