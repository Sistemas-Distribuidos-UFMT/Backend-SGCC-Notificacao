package br.com.ufmt.backendsgccnotificacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service // Anotação que define esta classe como um serviço gerenciado pelo Spring
public class EmailService {

    @Autowired // O Spring vai injetar automaticamente a instância do JavaMailSender configurada
    private JavaMailSender emailSender;

    public void enviarEmailSimples(String para, String assunto, String texto) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            // Substitua pelo seu e-mail de teste configurado no application.properties
            mensagem.setFrom("sgcc.notificacoes.teste@gmail.com");
            mensagem.setTo(para);
            mensagem.setSubject(assunto);
            mensagem.setText(texto);

            emailSender.send(mensagem);

            System.out.println("E-mail de notificação enviado com sucesso para: " + para);
        } catch (Exception e) {
            // Imprime o erro no console para ajudar a depurar
            System.err.println("Erro ao tentar enviar e-mail: " + e.getMessage());
        }
    }
}