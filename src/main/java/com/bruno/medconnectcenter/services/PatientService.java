package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.PatientDetailsDTO;
import com.bruno.medconnectcenter.dtos.PatientRequestDTO;
import com.bruno.medconnectcenter.dtos.PatientResponseDTO;
import com.bruno.medconnectcenter.entities.Patient;
import com.bruno.medconnectcenter.repositories.PatientRepository;
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
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public PatientDetailsDTO findById(Long id){
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Paciente não encontrado!")
        );

        return toDetailsDto(patient);
    }

    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> findAll(Pageable pageable){
        Page<Patient> listPatient = patientRepository.findAll(pageable);
        return listPatient.map(this::toResponseDto);
    }

    @Transactional
    public PatientResponseDTO insert(PatientRequestDTO dto){
        Patient patient = new Patient();
        dtoToEntity(dto, patient);
        patient = patientRepository.save(patient);
        return toResponseDto(patient);
    }

    @Transactional
    public PatientResponseDTO update(Long id, PatientRequestDTO dto){
        try{
            Patient patient = patientRepository.getReferenceById(id);
            dtoToEntity(dto, patient);
            patient = patientRepository.save(patient);
            return toResponseDto(patient);
        }
        catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Não foi possível atualizar o cadastro do paciente");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id){
        if(!patientRepository.existsById(id)){
            throw new EntityNotFoundException("Paciente não encontrado!");
        }

        try {
            patientRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não foi possível excluir o cadastro do paciente!");
        }
    }

    private PatientResponseDTO toResponseDto(Patient p){
        return new PatientResponseDTO(p.getId(), p.getName(), p.getPhone());
    }

    private PatientDetailsDTO toDetailsDto(Patient p){
        return new PatientDetailsDTO(
                p.getId(),
                p.getName(),
                p.getCpf(),
                p.getBirthDate(),
                p.getPhone(),
                p.getEmail(),
                p.getAddress());
    }

    private void dtoToEntity(PatientRequestDTO dto, Patient patient){
        patient.setName(dto.name());
        patient.setCpf(dto.cpf());
        patient.setBirthDate(dto.birthDate());
        patient.setPhone(dto.phone());
        patient.setEmail(dto.email());
        patient.setAddress(dto.address());
    }
}
