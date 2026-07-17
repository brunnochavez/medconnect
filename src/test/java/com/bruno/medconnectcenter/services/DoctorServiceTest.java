package com.bruno.medconnectcenter.services;
import com.bruno.medconnectcenter.dtos.DoctorResponseDetailsDTO;
import com.bruno.medconnectcenter.entities.Doctor;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void findById_DeveRetornarMedico_quandoIdExiste() {

        Doctor doctor = new Doctor();
        doctor.setId(1L);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        DoctorResponseDetailsDTO result = doctorService.findById(1L);

        assertEquals(1L, result.id());
    }

    @Test
    void findById_deveLancarEntityNotFoundException_quandoIdNaoExiste() {

        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> doctorService.deleteById(1L));
    }
}