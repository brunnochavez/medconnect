package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.Appointment;
import com.bruno.medconnectcenter.entities.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentDateTimeAndStatusIn(
            Long doctorId,
            LocalDateTime dateTime,
            List<AppointmentStatus> status
    );

    boolean existsByPatientIdAndAppointmentDateTimeAndStatus(
            Long patientId,
            LocalDateTime dateTime,
            AppointmentStatus status
    );

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctor.id = :doctorId " +
            "AND a.appointmentDateTime >= :startOfDay " +
            "AND a.appointmentDateTime < :endOfDay " +
            "AND a.status IN ('AGENDADA', 'CONFIRMADA')")
    List<Appointment> findBusyAppointmentsForDoctor(
            @Param("doctorId") Long doctorId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
