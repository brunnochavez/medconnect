package com.bruno.medconnectcenter.controllers;

import com.bruno.medconnectcenter.dtos.AppointmentRequestDTO;
import com.bruno.medconnectcenter.dtos.AppointmentResponseDTO;
import com.bruno.medconnectcenter.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> insert(@Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO dtoResponse = appointmentService.insert(dto);

        // Boa prática: Retornar o Status 201 (Created) com a URL do novo recurso no Header Location
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();

        return ResponseEntity.created(uri).body(dtoResponse);
    }
}