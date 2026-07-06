package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.AppointmentRequestDTO;
import com.bruno.medconnectcenter.dtos.AppointmentResponseDTO;
import com.bruno.medconnectcenter.entities.Appointment;
import com.bruno.medconnectcenter.entities.AppointmentStatus;
import com.bruno.medconnectcenter.entities.DoctorSchedule;
import com.bruno.medconnectcenter.repositories.AppointmentRepository;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.DoctorScheduleRepository;
import com.bruno.medconnectcenter.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Transactional(readOnly = true)
    public AppointmentResponseDTO findById(Long id){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Consulta não encontrada!")
        );
        return toResponseDto(appointment);
    }

    @Transactional(readOnly = true)
    public Page<AppointmentResponseDTO> findAll(Pageable pageable){
        Page<Appointment> listAppointments = appointmentRepository.findAll(pageable);
        return listAppointments.map(this::toResponseDto);
    }

    @Transactional
    public AppointmentResponseDTO insert(AppointmentRequestDTO dto){

        int minutes = dto.appointmentDateTime().getMinute();
        int seconds = dto.appointmentDateTime().getSecond();
        int nano = dto.appointmentDateTime().getNano();

        if(minutes % 15 != 0){
            throw new IllegalArgumentException("Agendamentos somente em intervalos de 15 minutos");
        }

        if(seconds != 0 || nano != 0){
            throw new IllegalArgumentException("Segundos e milissegundos não são permitidos");
        }

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

    @Transactional
    public AppointmentResponseDTO confirm(Long id){
        Appointment appointment = verify(id);

        if(appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("A consulta já foi realizada!");
        }

        if(appointment.getStatus() != AppointmentStatus.AGENDADA){
            throw new IllegalArgumentException("A consulta já foi realizada ou cancelada!");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMADA);
        appointment = appointmentRepository.save(appointment);
        return toResponseDto(appointment);
    }

    @Transactional
    public AppointmentResponseDTO cancel(Long id){
        Appointment appointment = verify(id);

        if(appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("A consulta já foi realizada!");
        }

        appointment.setStatus(AppointmentStatus.CANCELADA);
        appointment = appointmentRepository.save(appointment);
        return toResponseDto(appointment);
    }

    @Transactional
    public AppointmentResponseDTO carriedOut(Long id){
        Appointment appointment = verify(id);

        if(appointment.getStatus() != AppointmentStatus.REALIZADA){
            throw new IllegalArgumentException("A consulta foi realizada.");
        }

        appointment.setStatus(AppointmentStatus.REALIZADA);
        appointment = appointmentRepository.save(appointment);
        return toResponseDto(appointment);
    }

    public List<String> findAvailableSlots(Long doctorId, LocalDate date){

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        Optional<DoctorSchedule> scheduleOptions = doctorScheduleRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek);

        if(scheduleOptions.isEmpty()){
            return new ArrayList<>();
        }

        DoctorSchedule schedule = scheduleOptions.get();
        LocalTime startTime = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<Appointment> busyAppointments = appointmentRepository.findBusyAppointmentsForDoctor(doctorId, startOfDay, endOfDay);

        List<LocalTime> busyTimes = busyAppointments.stream()
                .map(app -> app.getAppointmentDateTime().toLocalTime())
                .toList();

        List<String> availableSlots = new ArrayList<>();
        LocalTime currentSlot = startTime;

        while (currentSlot.isBefore(endTime)) {
            if (!busyTimes.contains(currentSlot)) {
                availableSlots.add(currentSlot.toString());
            }
            currentSlot = currentSlot.plusMinutes(15);
        }

        return availableSlots;

    }

    private AppointmentResponseDTO toResponseDto(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getAppointmentDateTime(),
                appointment.getObservations(),
                appointment.getStatus(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId()
        );
    }

    private Appointment verify(Long id){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Consulta não encontrada!")
        );

        if(appointment.getStatus() == AppointmentStatus.CANCELADA){
            throw new IllegalArgumentException("A consulta encontra-se cancelada!");
        }
        return appointment;
    }
}
