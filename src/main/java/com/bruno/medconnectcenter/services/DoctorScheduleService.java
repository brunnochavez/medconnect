package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.DoctorScheduleRequestDTO;
import com.bruno.medconnectcenter.dtos.DoctorScheduleResponseDTO;
import com.bruno.medconnectcenter.entities.Doctor;
import com.bruno.medconnectcenter.entities.DoctorSchedule;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.DoctorScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public DoctorScheduleResponseDTO insert(DoctorScheduleRequestDTO dto){

        if(dto.startTime().isAfter(dto.endTime())){
            throw new IllegalArgumentException("O horário inicial informado é maior que o horário final");
        }

        try {
            Doctor doctor = doctorRepository.getReferenceById(dto.doctorId());

            DoctorSchedule doctorSchedule = new DoctorSchedule();
            doctorSchedule.setDoctor(doctor);
            doctorSchedule.setDayOfWeek(dto.dayOfWeek());
            doctorSchedule.setStartTime(dto.startTime());
            doctorSchedule.setEndTime(dto.endTime());

            doctorSchedule = doctorScheduleRepository.save(doctorSchedule);
            return toResponseDto(doctorSchedule);

        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Médico não encontrado!");
        }
    }

    @Transactional(readOnly = true)
    public List<DoctorScheduleResponseDTO> findByDoctorId(Long doctorId){

        if(!doctorRepository.existsById(doctorId)){
            throw new EntityNotFoundException("Médico não encontrado!");
        }

        List<DoctorSchedule> scheduleList = doctorScheduleRepository.findByDoctorId(doctorId);
        if(scheduleList.isEmpty()){
            throw new IllegalArgumentException("Nenhum horário cadastrado de atendimento para o médico pesquisado");
        }
       return scheduleList.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id){
        if(!doctorScheduleRepository.existsById(id)){
            throw new EntityNotFoundException("Horário não encontrado");
        }

        doctorScheduleRepository.deleteById(id);
    }

    private DoctorScheduleResponseDTO toResponseDto(DoctorSchedule doctorSchedule){
        return new DoctorScheduleResponseDTO(
                doctorSchedule.getId(),
                doctorSchedule.getDayOfWeek(),
                doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime(),
                doctorSchedule.getDoctor().getId()
        );
    }
}
