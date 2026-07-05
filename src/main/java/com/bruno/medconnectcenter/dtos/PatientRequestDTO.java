package com.bruno.medconnectcenter.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDate;

public record PatientRequestDTO(

        @NotBlank(message = "Nome é obrigatório.")
        String name,

        @NotBlank(message = "CPF obrigatório.")
        @CPF(message = "CPF informado é inválido!")
        String cpf,

        @NotBlank(message = "Data de nascimento obrigatória.")
        @Past(message = "Data de nascimento não pode estar no futuro.")
        LocalDate birthDate,

        @NotBlank(message = "Telefone obrigatório.")
        String phone,

        @NotBlank(message = "E-mail obrigatório.")
        @Email(message = "E-mail incorreto.")
        String email,

        @NotBlank(message = "Endereço é obrigatório")
        String address
) {
}
