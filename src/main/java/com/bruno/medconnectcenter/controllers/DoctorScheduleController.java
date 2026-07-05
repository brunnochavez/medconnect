package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.DoctorScheduleRequestDTO;
import com.bruno.medconnectcenter.dtos.DoctorScheduleResponseDTO;
import com.bruno.medconnectcenter.services.DoctorScheduleService;
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
public class DoctorScheduleController {

    private final DoctorScheduleService service;
    @GetMapping(value = "/doctors/{doctorId}")
    public ResponseEntity<List<DoctorScheduleResponseDTO>> findByDoctorId(@PathVariable Long doctorId) {
        List<DoctorScheduleResponseDTO> list = service.findByDoctorId(doctorId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<DoctorScheduleResponseDTO> insert(@Valid @RequestBody DoctorScheduleRequestDTO dto) {
        DoctorScheduleResponseDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}