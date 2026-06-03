package com.example.hospital.exception;

/**
 * Thrown when attempting to create a resource with a duplicate unique field (e.g., email).
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
