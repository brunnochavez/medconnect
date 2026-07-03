package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
}
