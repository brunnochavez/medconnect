package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.*;
import com.bruno.medconnectcenter.services.SpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SpecialtyResponseDTO> findById(@PathVariable Long id){
        SpecialtyResponseDTO dto = specialtyService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<SpecialtyResponseDTO>> findAll(Pageable pageable){
        Page<SpecialtyResponseDTO> listSpecialties = specialtyService.findAll(pageable);
        return ResponseEntity.ok(listSpecialties);
    }
    @PostMapping
    public ResponseEntity<SpecialtyResponseDTO> insert(@Valid @RequestBody SpecialtyRequestDTO dto){
        SpecialtyResponseDTO dtoResponse = specialtyService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SpecialtyResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SpecialtyRequestDTO dto){
        SpecialtyResponseDTO dtoResponse = specialtyService.update(id, dto);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        specialtyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
