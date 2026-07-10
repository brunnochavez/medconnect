package com.bruno.medconnectcenter.dtos;

import com.bruno.medconnectcenter.entities.Specialty;

import java.util.List;

public record DoctorResponseDTO(
        Long id,
        String name,
        String crm,
        List<SpecialtyResponseDTO> specialties
) {
}
