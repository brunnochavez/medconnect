package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.AppointmentRequestDTO;
import com.bruno.medconnectcenter.dtos.AppointmentResponseDTO;
import com.bruno.medconnectcenter.entities.Appointment;
import com.bruno.medconnectcenter.entities.AppointmentStatus;

import com.bruno.medconnectcenter.repositories.AppointmentRepository;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.DoctorScheduleRepository;
import com.bruno.medconnectcenter.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Transactional
    public AppointmentResponseDTO insert(AppointmentRequestDTO dto){

        boolean doctorConflict = appointmentRepository.existsByDoctorIdAndAppointmentDateTimeAndStatus(
          dto.doctorId(),
          dto.appointmentDateTime(),
          AppointmentStatus.AGENDADA
        );

        if(doctorConflict){
            throw new IllegalArgumentException("Médico já possui um agendamento para o horário informado!");
        }

        boolean patientConflict = appointmentRepository.existsByPatientIdAndAppointmentDateTimeAndStatus(
                dto.patientId(),
                dto.appointmentDateTime(),
                AppointmentStatus.AGENDADA
        );

        if(patientConflict){
            throw new IllegalArgumentException("O paciente já possui um agendamento para o horário informado!");
        }

        if(dto.appointmentDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("A data da consulta não pode estar no passado!");
        }

        java.time.DayOfWeek dayOfWeek = dto.appointmentDateTime().getDayOfWeek();
        java.time.LocalTime time = dto.appointmentDateTime().toLocalTime();

        boolean isDoctorAvailable = doctorScheduleRepository.existsAvailableSchedule(
                dto.doctorId(),
                dayOfWeek,
                time
        );

        if(!isDoctorAvailable){
            throw new IllegalArgumentException("Este médico não atende no dia ou horário informados!");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(dto.appointmentDateTime());
        appointment.setObservations(dto.observations());
        appointment.setStatus(AppointmentStatus.AGENDADA);
        appointment.setPatient(patientRepository.getReferenceById(dto.patientId()));
        appointment.setDoctor(doctorRepository.getReferenceById(dto.doctorId()));

        appointment = appointmentRepository.save(appointment);

        return toResponseDto(appointment);
    }

    private AppointmentResponseDTO toResponseDto(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getAppointmentDateTime(),
                appointment.getObservations(),
                appointment.getStatus(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId()
        );
    }
}
