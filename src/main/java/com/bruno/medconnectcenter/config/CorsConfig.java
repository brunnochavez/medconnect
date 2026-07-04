package com.bruno.medconnectcenter.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera todos os endpoints da nossa API
                .allowedOrigins("*") // Libera qualquer frontend tentar acessar (ideal para testes locais)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}