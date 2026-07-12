package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.*;
import com.bruno.medconnectcenter.services.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/specialties")
@RequiredArgsConstructor
@Tag(name = "Gerenciamento de especialidades")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca uma especialidade pelo Id")
    public ResponseEntity<SpecialtyResponseDTO> findById(@PathVariable Long id){
        SpecialtyResponseDTO dto = specialtyService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Busca paginada de especialidades")
    public ResponseEntity<Page<SpecialtyResponseDTO>> findAll(@ParameterObject Pageable pageable){
        Page<SpecialtyResponseDTO> listSpecialties = specialtyService.findAll(pageable);
        return ResponseEntity.ok(listSpecialties);
    }
    @PostMapping
    @Operation(summary = "Cadastrar uma especialidade")
    public ResponseEntity<SpecialtyResponseDTO> insert(@Valid @RequestBody SpecialtyRequestDTO dto){
        SpecialtyResponseDTO dtoResponse = specialtyService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar uma especialidade")
    public ResponseEntity<SpecialtyResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SpecialtyRequestDTO dto){
        SpecialtyResponseDTO dtoResponse = specialtyService.update(id, dto);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir especialidade pelo Id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        specialtyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
