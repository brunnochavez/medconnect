package com.bruno.medconnectcenter.config;
import com.bruno.medconnectcenter.entities.Doctor;
import com.bruno.medconnectcenter.entities.Patient;
import com.bruno.medconnectcenter.entities.Specialty;
import com.bruno.medconnectcenter.repositories.DoctorRepository;
import com.bruno.medconnectcenter.repositories.PatientRepository;
import com.bruno.medconnectcenter.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    // Injetamos os repositórios diretamente para pular a validação dos DTOs na inicialização
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. Populando Especialidades (A regra manda criar elas antes dos médicos)
        Specialty sp1 = new Specialty();
        sp1.setName("Cardiologia");

        Specialty sp2 = new Specialty();
        sp2.setName("Pediatria");

        Specialty sp3 = new Specialty();
        sp3.setName("Ortopedia");

        specialtyRepository.saveAll(Arrays.asList(sp1, sp2, sp3));

        // 2. Populando Pacientes
        Patient p1 = new Patient();
        p1.setName("Carlos Almeida");
        p1.setCpf("11122233344");
        p1.setBirthDate(LocalDate.of(1985, 10, 20));
        p1.setPhone("27999998888");
        p1.setEmail("carlos@email.com");
        p1.setAddress("Avenida Central, 100");

        Patient p2 = new Patient();
        p2.setName("Maria Silva");
        p2.setCpf("55566677788");
        p2.setBirthDate(LocalDate.of(1990, 5, 15));
        p2.setPhone("27988887777");
        p2.setEmail("maria@email.com");
        p2.setAddress("Rua Sul, 200");

        patientRepository.saveAll(Arrays.asList(p1, p2));

        // 3. Populando Médicos e associando as Especialidades (Many-to-Many em ação)
        Doctor d1 = new Doctor();
        d1.setName("Dra. Ana Silva");
        d1.setCrm("12345-SP");
        d1.setPhone("11988887777");
        d1.setEmail("ana@email.com");
        d1.getSpecialtyList().add(sp1); // Vinculando Cardiologia
        d1.getSpecialtyList().add(sp2); // Vinculando Pediatria

        Doctor d2 = new Doctor();
        d2.setName("Dr. João Souza");
        d2.setCrm("54321-RJ");
        d2.setPhone("21977776666");
        d2.setEmail("joao@email.com");
        d2.getSpecialtyList().add(sp3); // Vinculando Ortopedia

        doctorRepository.saveAll(Arrays.asList(d1, d2));
    }
}