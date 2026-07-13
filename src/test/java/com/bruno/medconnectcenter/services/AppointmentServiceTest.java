package com.bruno.medconnectcenter.services;

import com.bruno.medconnectcenter.dtos.AppointmentResponseDTO;
import com.bruno.medconnectcenter.entities.Appointment;
import com.bruno.medconnectcenter.entities.AppointmentStatus;
import com.bruno.medconnectcenter.entities.Doctor;
import com.bruno.medconnectcenter.entities.Patient;
import com.bruno.medconnectcenter.repositories.AppointmentRepository;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.DoctorScheduleRepository;
import com.bruno.medconnectcenter.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.Doc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorScheduleRepository doctorScheduleRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void confirm_deveAlterarStatusParaConfirmada_quandoConsultaEstaAgendadaENoFuturo(){

        Patient patient = new Patient();
        patient.setId(1L);

        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Appointment appointment = new Appointment();
        appointment.setId(10L);
        appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        appointment.setStatus(AppointmentStatus.AGENDADA);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        AppointmentResponseDTO result = appointmentService.confirm(10L);

        assertEquals(AppointmentStatus.CONFIRMADA, result.status());
        verify(appointmentRepository).save(appointment);

    }
}