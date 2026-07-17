# MedConnect API

Sistema de Agendamento de Consultas Médicas

API RESTful desenvolvida para o gerenciamento completo de agendamentos de uma clínica médica.

## O que já está implementado:

* **Arquitetura em Camadas:** Divisão entre Controllers, Services, Repositories e DTOs.
* **Tráfego Seguro:** Uso do padrão DTO para blindar as entidades do banco de dados.
* **Validação e Tratamento de Erros:** Handler global de exceções para respostas HTTP semânticas (400, 404, 409, etc).
* **Regras de Negócio:** Verificação de conflito de horário para médico e paciente, ciclo de vida completo da consulta (agendada → confirmada → realizada/cancelada) e reaproveitamento automático de horários liberados por cancelamento.
* **Prevenção de Concorrência:** Lock otimista (`@Version`) para evitar que edições simultâneas corrompam o estado da consulta.
* **Paginação:** Rotas de listagem otimizadas com o Spring Data Pageable.
* **Testes Unitários:** Cobertura iniciada com JUnit 5 e Mockito na camada de Service.

## Documentação da API (Swagger)

O projeto possui uma documentação construída com Swagger (springdoc-openapi).
Através dela, é possível visualizar todos os endpoints, regras de negócio e testar as requisições diretamente pelo navegador.

Para acessar localmente:

1. Execute o projeto na sua IDE (sobe com H2 em memória e dados de exemplo, sem precisar configurar banco).
2. Abra o navegador e acesse a URL: `http://localhost:8080/swagger-ui.html`

## Próximos Passos:

* Ampliar a cobertura de testes unitários.
* Segurança e Autenticação (Spring Security).

Fiz esse diagrama para mostrar como as tabelas se conversam:

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
