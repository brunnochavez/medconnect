# MedConnectCenter API 🩺

> Uma API RESTful robusta para gestão de agendamentos de uma clínica médica, desenvolvida com foco em integridade de dados, prevenção de concorrência e regras de negócio do mundo real.

## 💻 Sobre o Projeto

O MedConnectCenter não é apenas um simples cadastro (CRUD). Este projeto foi desenhado para resolver problemas reais de uma clínica médica, como sobreposição de horários e integridade do histórico de pacientes. A API gerencia o ciclo de vida completo de uma consulta médica (Agendada ➡️ Confirmada ➡️ Realizada / Cancelada), validando regras estritas de horário e disponibilidade.

## 🛠️ Tecnologias Utilizadas

* **Java 21:** Utilização de recursos modernos como `Records` para DTOs, garantindo imutabilidade.
* **Spring Boot 3:** Framework principal da aplicação.
* **Spring Data JPA & Hibernate:** Mapeamento Objeto-Relacional (ORM) e persistência de dados.
* **MySQL:** Banco de dados relacional (via Oracle JDBC / H2 para testes).
* **Spring Validation:** Validação de dados de entrada na camada de controle.
* **Swagger (Springdoc OpenAPI):** Documentação interativa da API.
* **Postman:** Coleção de requisições exportada para facilitar testes locais de endpoints e diagnóstico de *status codes*.

## 🏗️ Decisões Arquiteturais

Durante o desenvolvimento, algumas decisões arquiteturais importantes foram tomadas para garantir que o sistema se comportasse de forma previsível em um ambiente de produção (múltiplos usuários simultâneos):

* **Prevenção de *Race Conditions* (Condição de Corrida):**
  * **Lock Otimista (`@Version`):** Implementado no Hibernate para impedir que edições simultâneas corrompam o estado da consulta (ex: paciente e secretária tentando cancelar e confirmar a mesma consulta no mesmo exato milissegundo).
  * **Unique Constraints (Catraca do Banco):** Índices compostos no banco de dados para garantir matematicamente que dois pacientes não consigam reservar o mesmo médico, no mesmo dia e horário.
* **Soft Delete Estruturado:** Exclusão lógica de médicos e pacientes sem o uso de filtros globais de invisibilidade. Isso garante que pacientes antigos não percam seus relatórios e históricos de consultas com médicos que já foram inativados ou desligados da clínica.
* **Tratamento Global de Exceções (`@RestControllerAdvice`):** Padronização das respostas de erro da API. Em vez de expor as *stack traces* e *queries* SQL (evitando *Information Disclosure*), a API devolve mensagens amigáveis e *Status Codes* HTTP corretos (como `409 Conflict` para choques de agenda, `422 Unprocessable Entity` ou `404 Not Found`).
* **Padrão DTO (Data Transfer Object):** Separação estrita entre as entidades de banco de dados e os dados expostos na API, blindando a estrutura interna (como no relacionamento entre Médico, Paciente e Especialidades).

## 🚀 Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone [https://github.com/brunnochavez/medconnect.git](https://github.com/brunnochavez/medconnect.git)

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
