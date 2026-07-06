package com.bruno.medconnectcenter.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record DoctorRequestDTO(

        @NotBlank(message = "Nome do paciente é obrigatório.")
        String name,

        @NotBlank(message = "CRM é obrigatório.")
        @Size(min = 6)
        String crm,

        @NotBlank(message = "Telefone é obrigatório.")
        String phone,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail informa é inválido.")
        String email,

        @NotEmpty(message = "O médico deve possuir ao menos uma especialidade.")
        Set<Long> specialtyIds
){}
