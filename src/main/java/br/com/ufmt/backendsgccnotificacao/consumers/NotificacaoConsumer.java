package br.com.ufmt.backendsgccnotificacao.consumers;

import br.com.ufmt.backendsgccnotificacao.dtos.NotificacaoDTO;
import br.com.ufmt.backendsgccnotificacao.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "consultas.v1.notificacoes")
    public void ouvirMensagem(NotificacaoDTO dto) { // <--- MUDANÇA AQUI
        System.out.println("====================================================");
        System.out.println("MENSAGEM DTO RECEBIDA: " + dto.toString());
        System.out.println("====================================================");

        switch (dto.getTipo()) {
            case CONFIRMACAO_AGENDAMENTO:
                emailService.enviarEmailConfirmacao(dto); // RF3.1
                break;
            // Outros casos virão aqui
        }
    }

    @RabbitListener(queues = "consultas.v1.cancelamentos")
    public void ouvirMensagemCancelamento(NotificacaoDTO dto) {
        System.out.println("MENSAGEM DE CANCELAMENTO RECEBIDA: " + dto.toString());
        emailService.enviarEmailCancelamento(dto);
    }

    @RabbitListener(queues = "consultas.v1.lembretes")
    public void ouvirMensagemLembrete(NotificacaoDTO dto) {
        System.out.println("MENSAGEM DE LEMBRETE RECEBIDA: " + dto.toString());
        emailService.enviarEmailLembrete(dto);
    }
}