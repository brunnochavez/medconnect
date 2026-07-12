package com.bruno.medconnectcenter.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record DoctorRequestDTO(

        @NotBlank(message = "Nome do médico é obrigatório.")
        String name,

        @NotBlank(message = "CRM é obrigatório.")
        @Size(min = 6)
        String crm,

        @NotBlank(message = "Telefone é obrigatório.")
        String phone,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail informado é inválido.")
        String email,

        @NotEmpty(message = "O médico deve possuir ao menos uma especialidade.")
        Set<Long> specialtyIds
){}
