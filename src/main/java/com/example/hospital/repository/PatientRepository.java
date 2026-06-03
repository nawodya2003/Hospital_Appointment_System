package com.example.hospital.repository;

import com.example.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Patient entity operations.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find a patient by email address.
     * Used for uniqueness validation.
     */
    Optional<Patient> findByEmail(String email);
}
