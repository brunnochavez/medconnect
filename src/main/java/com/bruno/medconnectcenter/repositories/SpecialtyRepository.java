package com.bruno.medconnectcenter.repositories;
import com.bruno.medconnectcenter.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
}
