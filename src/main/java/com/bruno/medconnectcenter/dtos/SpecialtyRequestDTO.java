package com.bruno.medconnectcenter.dtos;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyRequestDTO(
        @NotBlank(message = "O campo nome não pode estar em branco!")
        String name
) {
}
