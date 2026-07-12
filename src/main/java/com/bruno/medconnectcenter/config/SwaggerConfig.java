package com.bruno.medconnectcenter.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MedConnectCenter API 🩺")
                        .version("1.0.0")
                        .description("""
                                **Sistema de Clínica Médica**
                                
                                ### Principais Funcionalidades:
                                * **Agendamento Inteligente:** O sistema calcula e expõe apenas horários realmente livres.
                                * **Bloqueio de Conflitos:** Impede que médicos ou pacientes tenham sobreposição de horários.
                                * **Horários Cravados:** Validação rigorosa para consultas em blocos exatos de 15 em 15 minutos (sem agendamentos "quebrados").
                                * **Máquina de Estados:** Fluxo de vida da consulta (AGENDADA ➡️ CONFIRMADA ➡️ REALIZADA / CANCELADA).
                                
                                Desenvolvido com **Java 21, Spring Boot 4 e Oracle Database** (H2 em memória no perfil de testes).
                                """)
                        .contact(new Contact()
                                .name("Bruno Oliveira")))
                .externalDocs(new ExternalDocumentation()
                        .description("Acesse o repositório do projeto back end no GitHub para ver o código fonte")
                        .url("https://github.com/brunnochavez/medconnect"));
    }
}