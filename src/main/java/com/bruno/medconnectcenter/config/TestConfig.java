package com.bruno.medconnectcenter.config;

import com.bruno.medconnectcenter.entities.*;
import com.bruno.medconnectcenter.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final AppointmentRepository appointmentRepository; // Injetado para popular as consultas

    @Override
    public void run(String... args) throws Exception {

        // =========================================================================
        // 1. POPULANDO ESPECIALIDADES
        // =========================================================================
        Specialty sp1 = new Specialty(); sp1.setName("Cardiologia");
        Specialty sp2 = new Specialty(); sp2.setName("Pediatria");
        Specialty sp3 = new Specialty(); sp3.setName("Ortopedia");
        Specialty sp4 = new Specialty(); sp4.setName("Dermatologia");
        Specialty sp5 = new Specialty(); sp5.setName("Ginecologia");

        specialtyRepository.saveAll(Arrays.asList(sp1, sp2, sp3, sp4, sp5));

        // =========================================================================
        // 2. POPULANDO PACIENTES
        // =========================================================================
        Patient p1 = new Patient();
        p1.setName("Carlos Almeida"); p1.setCpf("11122233344");
        p1.setBirthDate(LocalDate.of(1985, 10, 20)); p1.setPhone("27999998888");
        p1.setEmail("carlos@email.com"); p1.setAddress("Avenida Central, 100");

        Patient p2 = new Patient();
        p2.setName("Maria Silva"); p2.setCpf("55566677788");
        p2.setBirthDate(LocalDate.of(1990, 5, 15)); p2.setPhone("27988887777");
        p2.setEmail("maria@email.com"); p2.setAddress("Rua Sul, 200");

        Patient p3 = new Patient();
        p3.setName("Bruno Henrique"); p3.setCpf("22233344455");
        p3.setBirthDate(LocalDate.of(1993, 3, 12)); p3.setPhone("11977776666");
        p3.setEmail("bruno.henrique@email.com"); p3.setAddress("Alameda Flores, 45");

        Patient p4 = new Patient();
        p4.setName("Juliana Costa"); p4.setCpf("99988877766");
        p4.setBirthDate(LocalDate.of(1978, 11, 30)); p4.setPhone("21966665555");
        p4.setEmail("juliana.costa@email.com"); p4.setAddress("Rua das Palmeiras, 880");

        Patient p5 = new Patient();
        p5.setName("Lucas Oliveira"); p5.setCpf("44455566677");
        p5.setBirthDate(LocalDate.of(2002, 7, 22)); p5.setPhone("31955554444");
        p5.setEmail("lucas.oliver@email.com"); p5.setAddress("Av. Getúlio Vargas, 1012");

        patientRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        // =========================================================================
        // 3. POPULANDO MÉDICOS
        // =========================================================================
        Doctor d1 = new Doctor();
        d1.setName("Dra. Ana Silva"); d1.setCrm("12345-SP");
        d1.setPhone("11988887777"); d1.setEmail("ana@email.com");
        d1.getSpecialtyList().add(sp1); d1.getSpecialtyList().add(sp2);

        Doctor d2 = new Doctor();
        d2.setName("Dr. João Souza"); d2.setCrm("54321-RJ");
        d2.setPhone("21977776666"); d2.setEmail("joao@email.com");
        d2.getSpecialtyList().add(sp3);

        Doctor d3 = new Doctor();
        d3.setName("Dr. Marcos Reus"); d3.setCrm("98765-MG");
        d3.setPhone("31966664444"); d3.setEmail("marcos.reus@email.com");
        d3.getSpecialtyList().add(sp4);

        Doctor d4 = new Doctor();
        d4.setName("Dra. Camila Pitanga"); d4.setCrm("67890-ES");
        d4.setPhone("27955553333"); d4.setEmail("camila.pitanga@email.com");
        d4.getSpecialtyList().add(sp5);

        doctorRepository.saveAll(Arrays.asList(d1, d2, d3, d4));

        // =========================================================================
        // 4. POPULANDO AS GRADES DE HORÁRIO (DoctorSchedule)
        // =========================================================================
        DoctorSchedule schAna1 = new DoctorSchedule();
        schAna1.setDoctor(d1); schAna1.setDayOfWeek(DayOfWeek.MONDAY);
        schAna1.setStartTime(LocalTime.of(8, 0)); schAna1.setEndTime(LocalTime.of(12, 0));

        DoctorSchedule schAna2 = new DoctorSchedule();
        schAna2.setDoctor(d1); schAna2.setDayOfWeek(DayOfWeek.WEDNESDAY);
        schAna2.setStartTime(LocalTime.of(14, 0)); schAna2.setEndTime(LocalTime.of(18, 0));

        DoctorSchedule schJoao1 = new DoctorSchedule();
        schJoao1.setDoctor(d2); schJoao1.setDayOfWeek(DayOfWeek.TUESDAY);
        schJoao1.setStartTime(LocalTime.of(8, 0)); schJoao1.setEndTime(LocalTime.of(12, 0));

        DoctorSchedule schJoao2 = new DoctorSchedule();
        schJoao2.setDoctor(d2); schJoao2.setDayOfWeek(DayOfWeek.THURSDAY);
        schJoao2.setStartTime(LocalTime.of(13, 0)); schJoao2.setEndTime(LocalTime.of(17, 0));

        DoctorSchedule schMarcos = new DoctorSchedule();
        schMarcos.setDoctor(d3); schMarcos.setDayOfWeek(DayOfWeek.FRIDAY);
        schMarcos.setStartTime(LocalTime.of(8, 0)); schMarcos.setEndTime(LocalTime.of(16, 0));

        DoctorSchedule schCamilaSeg = new DoctorSchedule();
        schCamilaSeg.setDoctor(d4); schCamilaSeg.setDayOfWeek(DayOfWeek.MONDAY);
        schCamilaSeg.setStartTime(LocalTime.of(13, 0)); schCamilaSeg.setEndTime(LocalTime.of(18, 0));

        DoctorSchedule schCamilaTer = new DoctorSchedule();
        schCamilaTer.setDoctor(d4); schCamilaTer.setDayOfWeek(DayOfWeek.TUESDAY);
        schCamilaTer.setStartTime(LocalTime.of(13, 0)); schCamilaTer.setEndTime(LocalTime.of(18, 0));

        doctorScheduleRepository.saveAll(Arrays.asList(
                schAna1, schAna2, schJoao1, schJoao2, schMarcos, schCamilaSeg, schCamilaTer
        ));

        // =========================================================================
        // 5. POPULANDO AGENDAMENTOS (Para testar a tabela e máquina de estados no Front)
        // =========================================================================

        // Consulta no Passado -> REALIZADA
        Appointment app1 = new Appointment();
        app1.setPatient(p1); app1.setDoctor(d1);
        app1.setAppointmentDateTime(LocalDateTime.of(2026, 6, 10, 9, 0)); // Data no passado
        app1.setStatus(AppointmentStatus.REALIZADA);
        app1.setObservations("Paciente relatou dores no peito. Exames solicitados.");

        // Consulta no Passado -> CANCELADA
        Appointment app2 = new Appointment();
        app2.setPatient(p2); app2.setDoctor(d2);
        app2.setAppointmentDateTime(LocalDateTime.of(2026, 6, 15, 10, 0)); // Data no passado
        app2.setStatus(AppointmentStatus.CANCELADA);
        app2.setObservations("Paciente ligou para cancelar por imprevisto familiar.");

        // Consulta no Futuro -> AGENDADA
        Appointment app3 = new Appointment();
        app3.setPatient(p3); app3.setDoctor(d3);
        app3.setAppointmentDateTime(LocalDateTime.of(2026, 8, 20, 14, 0)); // Data no futuro
        app3.setStatus(AppointmentStatus.AGENDADA);
        app3.setObservations("Primeira consulta dermatológica.");

        // Consulta no Futuro Próximo -> CONFIRMADA
        Appointment app4 = new Appointment();
        app4.setPatient(p4); app4.setDoctor(d4);
        app4.setAppointmentDateTime(LocalDateTime.of(2026, 8, 5, 15, 30)); // Data no futuro
        app4.setStatus(AppointmentStatus.CONFIRMADA);
        app4.setObservations("Exames de rotina anual.");

        appointmentRepository.saveAll(Arrays.asList(app1, app2, app3, app4));
    }
}