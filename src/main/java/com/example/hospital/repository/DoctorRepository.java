package com.example.hospital.repository;

import com.example.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Doctor entity operations.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Find a doctor by email address.
     * Used for uniqueness validation.
     */
    Optional<Doctor> findByEmail(String email);
}
