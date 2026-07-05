package com.bruno.medconnectcenter.dtos;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record AppointmentRequestDTO(

        @NotBlank(message = "Data da consulta é obrigatória.")
        @Future(message = "A consulta deve ser agendada para uma data futura.")
        LocalDateTime appointmentDateTime,
        String observations,

        @NotBlank(message = "Obrigatório informar o paciente.")
        Long patientId,

        @NotBlank(message = "Obrigatório informar o médico,")
        Long doctorId
) {
}
