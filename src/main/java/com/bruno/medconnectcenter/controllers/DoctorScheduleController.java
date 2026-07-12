package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.DoctorScheduleRequestDTO;
import com.bruno.medconnectcenter.dtos.DoctorScheduleResponseDTO;
import com.bruno.medconnectcenter.services.DoctorScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/schedules")
@RequiredArgsConstructor
@Tag(name = "Gerenciamento da grade de horário dos médicos")
public class DoctorScheduleController {

    private final DoctorScheduleService service;

    @GetMapping(value = "/doctors/{doctorId}")
    @Operation(summary = "Buscar horários de um médico")
    public ResponseEntity<List<DoctorScheduleResponseDTO>> findByDoctorId(@PathVariable Long doctorId) {
        List<DoctorScheduleResponseDTO> list = service.findByDoctorId(doctorId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo horário")
    public ResponseEntity<DoctorScheduleResponseDTO> insert(@Valid @RequestBody DoctorScheduleRequestDTO dto) {
        DoctorScheduleResponseDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar um horário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}