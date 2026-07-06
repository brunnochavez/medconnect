package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.DoctorResponseDetailsDTO;
import com.bruno.medconnectcenter.dtos.DoctorRequestDTO;
import com.bruno.medconnectcenter.dtos.DoctorResponseDTO;
import com.bruno.medconnectcenter.services.DoctorService;
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
@RequestMapping(value = "/doctors")
@RequiredArgsConstructor
@Tag(name = "Gerenciamento de médicos")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar um médico pelo Id")
    public ResponseEntity<DoctorResponseDetailsDTO> findById(@PathVariable Long id){
        DoctorResponseDetailsDTO dto = doctorService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Busca paginada de médicos")
    public ResponseEntity<Page<DoctorResponseDTO>> findAll(Pageable pageable){
        Page<DoctorResponseDTO> listDoctors = doctorService.findAll(pageable);
        return ResponseEntity.ok(listDoctors);
    }
    @PostMapping
    @Operation(summary = "Cadastrar um médico")
    public ResponseEntity<DoctorResponseDTO> insert(@Valid @RequestBody DoctorRequestDTO dto){
        DoctorResponseDTO dtoResponse = doctorService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar o cadastro de um médico")
    public ResponseEntity<DoctorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO dto){
        DoctorResponseDTO dtoResponse = doctorService.update(id, dto);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir o cadastro de um médico")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        doctorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
