package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
