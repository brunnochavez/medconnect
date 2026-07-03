package com.bruno.medconnectcenter.dtos;

import java.util.Set;

public record DoctorRequestDTO(
        String name,
        String crm,
        String phone,
        String email,
        Set<Long> specialtyIds
) {
}
