package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.PatientRequestDTO;
import com.bruno.medconnectcenter.dtos.PatientResponseDTO;
import com.bruno.medconnectcenter.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(value = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable Long id){
        PatientResponseDTO dto = patientService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> findAll(Pageable pageable){
        Page<PatientResponseDTO> listPatients = patientService.findAll(pageable);
        return ResponseEntity.ok(listPatients);
    }
    @PostMapping
    public ResponseEntity<PatientResponseDTO> insert(@Valid @RequestBody PatientRequestDTO dto){
        PatientResponseDTO dtoResponse = patientService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO dto){
        PatientResponseDTO dtoResponse = patientService.update(id, dto);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
