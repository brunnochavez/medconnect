package com.bruno.medconnectcenter.dtos;
import java.time.LocalDate;

public record PatientDetailsDTO(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String phone,
        String email,
        String address
) {
}
