# MedConnect - Sistema de Gestão de Clínica Médica 🩺

O **MedConnect** é um sistema completo (Backend em Spring Boot e Frontend em React) desenvolvido para gerenciar as operações diárias de uma clínica médica moderna. 

O foco central da arquitetura de backend deste projeto é a **consistência de dados e a blindagem de regras de negócio** em um domínio complexo de agendamentos. A API não atua apenas como um CRUD, mas como um motor inteligente que prevê conflitos, calcula blocos de tempo exatos (slots) e garante a integridade da agenda médica.

---

## 🚀 Diferenciais Técnicos (Highlights)

* **Motor de Agendamento Proativo:** Algoritmo que cruza a grade de trabalho do médico com as consultas já marcadas (evitando o problema de N+1 Queries) para devolver ao front-end apenas os horários rigorosamente livres.
* **Fatiamento de Tempo Discreto:** Validações matemáticas que travam a agenda em blocos exatos de 15 em 15 minutos, impedindo dados corrompidos (ex: requisições com segundos ou minutos quebrados).
* **Fail-Fast Validation:** Uso rigoroso de `Jakarta Bean Validation` (`@NotNull`, `@NotBlank`) e validações nativas do Java (`java.time`) antes de qualquer ida ao banco de dados, otimizando a performance do servidor.
* **Database Seeding Automático:** Classe `TestConfig` estruturada via `CommandLineRunner` para popular o banco em perfis de teste, garantindo um ambiente pronto para uso no Front-end ou Postman assim que o servidor sobe.

---

## ⚙️ Funcionalidades Principais

| Funcionalidade | Descrição |
| :--- | :--- |
| **Gestão de Pacientes e Médicos** | Cadastro estruturado via DTOs de pacientes e médicos. |
| **Grade de Trabalho (Schedules)** | Definição de horários de atendimento específicos por dia da semana para cada médico. |
| **Motor de Horários Livres** | Endpoint exclusivo que calcula e retorna apenas os horários disponíveis de um médico em um dia específico. |
| **Agendamento com Proteção** | Marcação de consultas validando conflitos do médico, do paciente, bloqueio de datas passadas e obediência à grade de trabalho. |
| **Máquina de Estados** | Gerenciamento do ciclo de vida da consulta: `AGENDADA`, `CONFIRMADA`, `CANCELADA` ou `REALIZADA`. |

---

## 🛡️ Regras de Negócio e Validações

1. **Sem Conflitos Simultâneos:** Um médico não pode ter dois pacientes no mesmo horário, e um paciente não pode ter duas consultas ao mesmo tempo.
2. **Intervalos Rígidos:** Consultas ocorrem estritamente de 15 em 15 minutos. Tolerância zero para dados com segundos e milissegundos.
3. **Bloqueio Temporal:** Consultas só podem ser agendadas para datas e horários no futuro.
4. **Alinhamento com a Grade:** O agendamento deve obrigatoriamente cair dentro da janela de disponibilidade (dia da semana, hora início e fim) da agenda daquele médico.

---

## 💻 Stack Tecnológica

**Backend:**
* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3+
* **Persistência:** Spring Data JPA (Hibernate)
* **Banco de Dados:** MySQL / PostgreSQL
* **Documentação:** Springdoc OpenAPI (Swagger UI)

**Frontend:**
* **Biblioteca:** React (com Vite)
* **Estilização:** Tailwind CSS (Design SaaS Moderno, Glassmorphism)
* **Integração:** Axios / Fetch API

---

Próximos Passos (Work in Progress)
[ ] Tratamento Global de Exceções (@RestControllerAdvice): Interceptar as regras de negócio barradas (ex: IllegalArgumentException) e falhas de validação dos DTOs para retornar payloads JSON limpos, padronizados e com status HTTP adequados (400 Bad Request, 404 Not Found).

[ ] Segurança com JWT: Implementar Spring Security para controle de acesso (Login de Recepcionistas vs. Portal do Paciente).

[ ] Testes Automatizados: Cobertura de testes unitários com JUnit 5 e Mockito na camada de Service.

## 🗺️ Estrutura de Entidades (Diagrama)

```mermaid
classDiagram
    class Patient {
        +Long id
        +String name
        +String cpf
        +LocalDate birthDate
        +String phone
        +String email
        +String address
    }

    class Doctor {
        +Long id
        +String name
        +String crm
        +String phone
        +String email
    }

    class Specialty {
        +Long id
        +String name
    }

    class Appointment {
        +Long id
        +LocalDateTime appointmentDateTime
        +String observations
        +AppointmentStatus status
    }

    class DoctorSchedule {
        +Long id
        +DayOfWeek dayOfWeek
        +LocalTime startTime
        +LocalTime endTime
    }

    class AppointmentStatus {
        <<enumeration>>
        AGENDADA
        CONFIRMADA
        CANCELADA
        REALIZADA
    }

    class DayOfWeek {
        <<enumeration>>
        MONDAY
        ...
        SUNDAY
    }

    Patient "1" --> "*" Appointment : has
    Doctor "1" --> "*" Appointment : performs
    Doctor "*" --> "*" Specialty : has
    Doctor "1" --> "*" DoctorSchedule : defines
