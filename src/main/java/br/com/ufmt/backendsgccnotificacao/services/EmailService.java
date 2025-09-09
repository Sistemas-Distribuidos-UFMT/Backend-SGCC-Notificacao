package br.com.ufmt.backendsgccnotificacao.services;

import br.com.ufmt.backendsgccnotificacao.dtos.NotificacaoDTO;
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

    public void enviarEmailConfirmacao(NotificacaoDTO dto) {
        String assunto = "Consulta Agendada com Sucesso!";
        String texto = String.format(
                "Olá, %s!\n\nSua consulta com Dr(a). %s foi agendada com sucesso para o dia %s.\n\nAtenciosamente,\nSGCC.",
                dto.getNomePaciente(),
                dto.getNomeMedico(),
                dto.getDataConsulta().toLocalDate().toString() // Formata para mostrar apenas a data
        );
        enviarEmailSimples(dto.getDestinatarioEmail(), assunto, texto);
    }


    public void enviarEmailCancelamento(NotificacaoDTO dto) {
        String assunto = "Sua Consulta foi Cancelada";
        String texto = String.format(
                "Olá, %s.\n\nInformamos que sua consulta com Dr(a). %s que estava agendada para o dia %s foi cancelada.\n\nPara mais informações, entre em contato.\nSGCC.",
                dto.getNomePaciente(),
                dto.getNomeMedico(),
                dto.getDataConsulta().toLocalDate().toString()
        );
        enviarEmailSimples(dto.getDestinatarioEmail(), assunto, texto);
    }


    public void enviarEmailLembrete(NotificacaoDTO dto) {
        String assunto = "Lembrete de Consulta";
        String texto = String.format(
                "Olá, %s!\n\nEste é um lembrete sobre sua consulta com Dr(a). %s amanhã, dia %s.\n\nAtenciosamente,\nSGCC.",
                dto.getNomePaciente(),
                dto.getNomeMedico(),
                dto.getDataConsulta().toLocalDate().toString()
        );
        enviarEmailSimples(dto.getDestinatarioEmail(), assunto, texto);
    }
}