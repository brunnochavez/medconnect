package com.bruno.medconnectcenter.controllers;
import com.bruno.medconnectcenter.dtos.DoctorRequestDTO;
import com.bruno.medconnectcenter.dtos.DoctorResponseDTO;
import com.bruno.medconnectcenter.services.DoctorService;
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
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<DoctorResponseDTO> findById(@PathVariable Long id){
        DoctorResponseDTO dto = doctorService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDTO>> findAll(Pageable pageable){
        Page<DoctorResponseDTO> listDoctors = doctorService.findAll(pageable);
        return ResponseEntity.ok(listDoctors);
    }
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> insert(@Valid @RequestBody DoctorRequestDTO dto){
        DoctorResponseDTO dtoResponse = doctorService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DoctorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO dto){
        DoctorResponseDTO dtoResponse = doctorService.update(id, dto);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        doctorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
