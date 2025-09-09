package br.com.ufmt.backendsgccnotificacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Habilita a execução de tarefas agendadas
@EnableCaching    // Habilita o suporte a cache do Spring
public class BackendSgccNotificacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSgccNotificacaoApplication.class, args);
    }
}