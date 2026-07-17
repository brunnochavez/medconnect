package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.DoctorResponseDetailsDTO;
import com.bruno.medconnectcenter.dtos.DoctorRequestDTO;
import com.bruno.medconnectcenter.dtos.DoctorResponseDTO;
import com.bruno.medconnectcenter.dtos.SpecialtyResponseDTO;
import com.bruno.medconnectcenter.entities.Doctor;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;

    @Transactional(readOnly = true)
    public DoctorResponseDetailsDTO findById(Long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Médico não encontrado!")
        );
        return toDetailsDto(doctor);
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponseDTO> findAll(Pageable pageable){
        Page<Doctor> listDoctors = doctorRepository.findAllByActiveTrue(pageable);
        return listDoctors.map(this::toResponseDto);
    }

    @Transactional
    public DoctorResponseDTO insert(DoctorRequestDTO dto){
        Doctor doctor = new Doctor();
        dtoToEntity(dto, doctor);
        for(Long specialtyId : dto.specialtyIds()){
            doctor.getSpecialtyList().add(specialtyRepository.getReferenceById(specialtyId));
        }
        doctor = doctorRepository.save(doctor);
        return toResponseDto(doctor);
    }

    @Transactional
    public DoctorResponseDTO update(Long id, DoctorRequestDTO dto){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Médico não encontrado!")
        );
        dtoToEntity(dto, doctor);
        doctor.getSpecialtyList().clear();
        for(Long specialtyId : dto.specialtyIds()){
            doctor.getSpecialtyList().add(specialtyRepository.getReferenceById(specialtyId));
        }
        doctor = doctorRepository.save(doctor);
        return toResponseDto(doctor);
    }

    @Transactional
    public void deleteById(Long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Médico não encontrado!")
        );

        doctor.setActive(false);
        doctorRepository.save(doctor);
    }

    private DoctorResponseDTO toResponseDto(Doctor doctor){
       List<SpecialtyResponseDTO> specialtyList = doctor.getSpecialtyList().stream().map(
               specialty -> new SpecialtyResponseDTO(specialty.getId(), specialty.getName())
       ).toList();

        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getCrm(),
                specialtyList
        );
    }

    private DoctorResponseDetailsDTO toDetailsDto(Doctor doctor){
        Set<SpecialtyResponseDTO> specialtyDto = doctor.getSpecialtyList().stream()
                .map(specialty -> new SpecialtyResponseDTO(specialty.getId(), specialty.getName()))
                .collect(Collectors.toSet());

        return new DoctorResponseDetailsDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getCrm(),
                doctor.getPhone(),
                doctor.getEmail(),
                specialtyDto
        );
    }

    private void dtoToEntity(DoctorRequestDTO dto, Doctor doctor){
        doctor.setName(dto.name());
        doctor.setCrm(dto.crm());
        doctor.setPhone(dto.phone());
        doctor.setEmail(dto.email());
    }
}
