package br.com.ufmt.backendsgccnotificacao.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "consultas.v1.notificacoes";
    public static final String QUEUE_CANCELAMENTOS = "consultas.v1.cancelamentos";
    public static final String QUEUE_LEMBRETES = "consultas.v1.lembretes";

    // NOVO BEAN PARA O CONVERSOR JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // BEANS DAS FILAS (JÁ EXISTENTES)
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Queue cancelamentosQueue() {
        return new Queue(QUEUE_CANCELAMENTOS, true);
    }

    @Bean
    public Queue lembretesQueue() {
        return new Queue(QUEUE_LEMBRETES, true);
    }
}