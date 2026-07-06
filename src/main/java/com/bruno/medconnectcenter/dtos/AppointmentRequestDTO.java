package com.bruno.medconnectcenter.dtos;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(

        @NotNull(message = "Data da consulta é obrigatória.")
        @Future(message = "A consulta deve ser agendada para uma data futura.")
        LocalDateTime appointmentDateTime,
        String observations,

        @NotNull(message = "Obrigatório informar o paciente.")
        Long patientId,

        @NotNull(message = "Obrigatório informar o médico,")
        Long doctorId
) {
}
