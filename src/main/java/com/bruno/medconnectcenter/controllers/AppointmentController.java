package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.AppointmentRequestDTO;
import com.bruno.medconnectcenter.dtos.AppointmentResponseDTO;
import com.bruno.medconnectcenter.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Operações relacionadas a consultas médicas")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    @Operation(summary = "Buscar consultas praginadas")
    public ResponseEntity<Page<AppointmentResponseDTO>> findAll(Pageable pageable) {
        Page<AppointmentResponseDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar uma consulta por Id")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Agendar uma nova consulta")
    public ResponseEntity<AppointmentResponseDTO> insert(@Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PatchMapping(value = "/{id}/confirm")
    @Operation(summary = "Confirmar uma consulta agendada")
    public ResponseEntity<AppointmentResponseDTO> confirm(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.confirm(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(value = "/{id}/cancel")
    @Operation(summary = "Cancelar uma consulta agendada")
    public ResponseEntity<AppointmentResponseDTO> cancel(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.cancel(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(value = "/{id}/finish")
    @Operation(summary = "Finalizar uma consulta agendada")
    public ResponseEntity<AppointmentResponseDTO> carriedOut(@PathVariable Long id) {
        AppointmentResponseDTO dto = service.carriedOut(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/available-slots")
    @Operation(summary = "Buscar horários disponíveis de um médico", description = "retorna uma lista com todos os horários exatos em que um médico " +
            "está livre para atender em uma data específica, considerando intervalos de 15 minutos.")
    public ResponseEntity<List<String>> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<String> slots = service.findAvailableSlots(doctorId, date);
        return ResponseEntity.ok(slots);
    }
}