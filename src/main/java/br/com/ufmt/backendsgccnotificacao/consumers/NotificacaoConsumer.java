package br.com.ufmt.backendsgccnotificacao.consumers;


import br.com.ufmt.backendsgccnotificacao.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Anotação que define esta classe como um componente gerenciado pelo Spring
public class NotificacaoConsumer {

    @Autowired
    private EmailService emailService;

    // Esta anotação transforma o método em um ouvinte da fila especificada.
    // O nome da fila deve ser o mesmo usado pelo serviço que envia a mensagem.
    @RabbitListener(queues = "consultas.v1.notificacoes")
    public void ouvirMensagem(String mensagem) {
        System.out.println("====================================================");
        System.out.println("MENSAGEM RECEBIDA DA FILA: " + mensagem);
        System.out.println("====================================================");

        // Lógica de teste:
        // Por enquanto, vamos assumir que a mensagem é o próprio e-mail do destinatário
        String emailDoPaciente = mensagem;
        String assunto = "Consulta Agendada com Sucesso!";
        String corpo = "Olá! Sua consulta foi agendada no SGCC. Em breve enviaremos mais detalhes.";

        emailService.enviarEmailSimples(emailDoPaciente, assunto, corpo);
    }
}