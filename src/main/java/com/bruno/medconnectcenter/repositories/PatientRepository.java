package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
