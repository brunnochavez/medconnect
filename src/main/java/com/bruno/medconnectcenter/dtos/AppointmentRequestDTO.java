package com.bruno.medconnectcenter.dtos;
import java.time.LocalDateTime;

public record AppointmentRequestDTO(

        LocalDateTime appointmentDateTime,
        String observations,
        Long patientId,
        Long doctorId
) {
}
