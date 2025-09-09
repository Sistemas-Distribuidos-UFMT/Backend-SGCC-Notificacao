package br.com.ufmt.backendsgccnotificacao.consumers;

import br.com.ufmt.backendsgccnotificacao.dtos.ConsultaStatusDTO;
import br.com.ufmt.backendsgccnotificacao.scheduler.LembreteCacheService;
import br.com.ufmt.backendsgccnotificacao.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoConsumer {

    @Autowired
    private EmailService emailService;

    @Autowired
    private LembreteCacheService lembreteCacheService;

    // Este único listener agora ouvirá a fila principal
    @RabbitListener(queues = "${spring.rabbitmq.queue}") // Usando a fila do application.properties
    public void ouvirMensagemDeConsulta(ConsultaStatusDTO dto) {
        System.out.println("====================================================");
        System.out.println("MENSAGEM RECEBIDA: " + dto.toString());
        System.out.println("====================================================");

        // Verifica a situação da consulta para decidir o que fazer
        if ("AGENDADA".equalsIgnoreCase(dto.getSituacao())) {
            // Requisito RF3.1
            emailService.enviarEmailConfirmacao(dto);
            // Adiciona no cache para o lembrete (RF3.3)
            lembreteCacheService.adicionarConsultaParaLembrete(dto);

        } else if ("CANCELADA".equalsIgnoreCase(dto.getSituacao())) {
            // Requisito RF3.2
            emailService.enviarEmailCancelamento(dto);
            // Remove do cache para não enviar lembrete
            lembreteCacheService.removerConsultaDoLembrete(dto);
        }
    }
}