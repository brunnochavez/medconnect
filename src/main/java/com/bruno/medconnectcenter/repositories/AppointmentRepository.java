package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.Appointment;
import com.bruno.medconnectcenter.entities.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Boolean existsByDoctorIdAndAppointmentDateTimeAndStatus(
            Long doctorId,
            LocalDateTime dateTime,
            AppointmentStatus status
    );

    Boolean existsByPatientIdAndAppointmentDateTimeAndStatus(
            Long patientId,
            LocalDateTime dateTime,
            AppointmentStatus status
    );
}
