package com.bruno.medconnectcenter.dtos;

import java.util.Set;

public record DoctorResponseDetailsDTO(
        Long id,
        String name,
        String crm,
        String phone,
        String email,
        Set<SpecialtyResponseDTO> specialties
) {
}
