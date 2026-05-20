package com.example.hospital_appointment_system.dto.doctor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String phoneNumber;
}
