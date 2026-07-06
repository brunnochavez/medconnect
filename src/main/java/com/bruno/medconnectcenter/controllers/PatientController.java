package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.PatientDetailsDTO;
import com.bruno.medconnectcenter.dtos.PatientRequestDTO;
import com.bruno.medconnectcenter.dtos.PatientResponseDTO;
import com.bruno.medconnectcenter.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Gerenciamento de pacientes")
public class PatientController {

    private final PatientService patientService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar um paciente pelo Id")
    public ResponseEntity<PatientDetailsDTO> findById(@PathVariable Long id){
        PatientDetailsDTO dto = patientService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Busca paginada de pacientes")
    public ResponseEntity<Page<PatientResponseDTO>> findAll(Pageable pageable){
        Page<PatientResponseDTO> listPatients = patientService.findAll(pageable);
        return ResponseEntity.ok(listPatients);
    }
    @PostMapping
    @Operation(summary = "Cadastrar um paciente")
    public ResponseEntity<PatientResponseDTO> insert(@Valid @RequestBody PatientRequestDTO dto){
        PatientResponseDTO dtoResponse = patientService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar o cadastro de um paciente")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO dto){
        PatientResponseDTO dtoResponse = patientService.update(id, dto);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Exclui o cadastro de um paciente")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
