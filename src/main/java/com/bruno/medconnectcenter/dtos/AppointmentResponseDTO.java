package com.bruno.medconnectcenter.dtos;
import com.bruno.medconnectcenter.entities.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(

        Long id,
        LocalDateTime appointmentDateTime,
        String observations,
        AppointmentStatus status,
        Long patientId,
        Long doctorId
) {
}
