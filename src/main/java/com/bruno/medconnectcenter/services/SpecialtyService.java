package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.SpecialtyRequestDTO;
import com.bruno.medconnectcenter.dtos.SpecialtyResponseDTO;
import com.bruno.medconnectcenter.entities.Specialty;
import com.bruno.medconnectcenter.repositories.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Transactional(readOnly = true)
    public SpecialtyResponseDTO findById(Long id){
        Specialty specialty = specialtyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Especialidade não encontrada!")
        );
        return new SpecialtyResponseDTO(specialty.getId(), specialty.getName());
    }

    @Transactional(readOnly = true)
    public Page<SpecialtyResponseDTO> findAll(Pageable pageable){
        Page<Specialty> listSpecialty = specialtyRepository.findAll(pageable);
        return listSpecialty.map(specialty -> new SpecialtyResponseDTO(specialty.getId(), specialty.getName()));
    }

    @Transactional
    public SpecialtyResponseDTO insert(SpecialtyRequestDTO dto){
        Specialty specialty = new Specialty();
        specialty.setName(dto.name());
        specialty = specialtyRepository.save(specialty);
        return new SpecialtyResponseDTO(specialty.getId(), specialty.getName());
    }

    @Transactional
    public SpecialtyResponseDTO update(Long id, SpecialtyRequestDTO dto){
        Specialty specialty = specialtyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Especialidade não encontrada!")
        );
        specialty.setName(dto.name());
        specialty = specialtyRepository.save(specialty);
        return new SpecialtyResponseDTO(specialty.getId(), specialty.getName());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id){
        if(!specialtyRepository.existsById(id)){
            throw new EntityNotFoundException("Especialidade não encontrada!");
        }
        try {
            specialtyRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não foi possível excluir o cadastrado da especialidade!");
        }
    }
}
