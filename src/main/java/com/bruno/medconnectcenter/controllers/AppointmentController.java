package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.AppointmentRequestDTO;
import com.bruno.medconnectcenter.dtos.AppointmentResponseDTO;
import com.bruno.medconnectcenter.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> findAll(Pageable pageable) {
        Page<AppointmentResponseDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> insert(@Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PatchMapping(value = "/{id}/confirm")
    public ResponseEntity<AppointmentResponseDTO> confirm(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.confirm(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(value = "/{id}/cancel")
    public ResponseEntity<AppointmentResponseDTO> cancel(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.cancel(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(value = "/{id}/finish")
    public ResponseEntity<AppointmentResponseDTO> carriedOut(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.carriedOut(id);
        return ResponseEntity.ok(dto);
    }
}