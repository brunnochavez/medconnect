package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
