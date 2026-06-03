package com.example.hospital.repository;

import com.example.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Appointment entity operations.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Find all appointments for a specific doctor.
     */
    List<Appointment> findByDoctorId(Long doctorId);

    /**
     * Find all appointments on a specific date.
     */
    List<Appointment> findByAppointmentDate(LocalDate date);

    /**
     * Find all appointments for a specific doctor on a specific date.
     * Used for overlap checking.
     */
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);
}
