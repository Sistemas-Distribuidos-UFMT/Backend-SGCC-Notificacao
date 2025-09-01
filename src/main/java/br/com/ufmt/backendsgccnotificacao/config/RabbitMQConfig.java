package br.com.ufmt.backendsgccnotificacao.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define o nome da fila como uma constante para evitar erros de digitação
    public static final String QUEUE_NAME = "consultas.v1.notificacoes";

    @Bean
    public Queue queue() {
        // O segundo parâmetro "true" torna a fila durável (não some se o RabbitMQ reiniciar)
        return new Queue(QUEUE_NAME, true);
    }
}