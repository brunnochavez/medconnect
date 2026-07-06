# MedConnect - Sistema de Clínica Médica 🩺

Olá! Esse é o meu projeto de portfólio: um sistema fullstack (Spring Boot + React) para gerenciar agendamentos de uma clínica médica. 

O meu maior desafio (e foco) nesse projeto não foi só fazer um CRUD simples, mas sim construir uma **lógica de agendamento que realmente funcione na vida real**, sem deixar os horários entrarem em conflito!

---

## 🚀 O que o projeto faz de mais legal?

* **Agendamento Inteligente:** Em vez de deixar o usuário digitar qualquer data e dar erro, o back-end calcula e devolve para o front-end apenas os botõezinhos com os horários que estão *realmente* livres.
* **Bloqueio de Conflitos:** O sistema não deixa duas pessoas marcarem com o mesmo médico na mesma hora (e nem o mesmo paciente marcar duas consultas no mesmo horário).
* **Horários cravados:** Fiz uma lógica no Java que obriga as consultas a durarem blocos exatos de 15 em 15 minutos. Nada de agendamentos quebrados tipo "08:03".
* **Banco já populado:** Configurei o Spring para já rodar inserindo médicos, pacientes e algumas consultas no banco de dados. Assim fica muito mais fácil de testar a aplicação!

---

## ⚙️ Funcionalidades

* **Cadastro:** Dá para salvar e listar Pacientes (com CPF) e Médicos (com CRM e Especialidades).
* **Grade do Médico:** Cada médico tem seus dias e horários de trabalho configurados (ex: Dra. Ana só atende segunda e quarta de manhã).
* **Controle de Status:** A consulta nasce como `AGENDADA`, pode mudar para `CONFIRMADA`, e depois ir para `REALIZADA` ou `CANCELADA`.

---

## 💻 Tecnologias que utilizei

**Back-end:**
* Java 17 + Spring Boot 3
* Spring Data JPA (Hibernate)
* Banco de Dados: MySQL (e H2 para testes)
* Validações com Jakarta Validation (`@NotNull`, `@NotBlank`)
* Documentação automática com Swagger (Springdoc)

**Front-end:**
* React.js (com Vite)
* Tailwind CSS para deixar a interface bonita e moderna
* Axios para fazer as requisições para a API

---

O que estou fazendo agora (Próximos Passos)
Como todo projeto de estudos, ele está sempre evoluindo. Minhas próximas tarefas são:

[ ] Melhorar os erros da API: Criar uma classe global de tratamento de exceções (usando @RestControllerAdvice) para trocar aqueles erros 500 feios do Java por mensagens JSON bonitinhas (ex: Erro 400 avisando que o horário está lotado).

[ ] Segurança (Login): Colocar Spring Security e JWT para separar quem é paciente de quem é recepcionista.

[ ] Testes: Escrever teste

## 🗺️ Como o Banco de Dados foi pensado

Fiz esse diagrama para mostrar como as tabelas se conversam:

```mermaid
classDiagram
    class Patient {
        +Long id
        +String name
        +String cpf
        ...
    }

    class Doctor {
        +Long id
        +String name
        +String crm
        ...
    }

    class Specialty {
        +Long id
        +String name
    }

    class Appointment {
        +Long id
        +LocalDateTime appointmentDateTime
        +AppointmentStatus status
    }

    class DoctorSchedule {
        +Long id
        +DayOfWeek dayOfWeek
        +LocalTime startTime
        +LocalTime endTime
    }

    Patient "1" --> "*" Appointment : tem
    Doctor "1" --> "*" Appointment : atende
    Doctor "*" --> "*" Specialty : possui
    Doctor "1" --> "*" DoctorSchedule : trabalha
