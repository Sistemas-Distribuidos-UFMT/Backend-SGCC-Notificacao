package br.com.ufmt.backendsgccnotificacao.services;

import br.com.ufmt.backendsgccnotificacao.dtos.ConsultaStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    // Injeta o e-mail remetente a partir do application.properties
    @Value("${spring.mail.username}")
    private String remetente;

    /**
     * Envia um e-mail de confirmação quando uma consulta é agendada.
     * Atende ao requisito RF3.1.
     *
     * @param dto O DTO contendo os detalhes da consulta agendada.
     */
    public void enviarEmailConfirmacao(ConsultaStatusDTO dto) {
        String assunto = "Consulta Agendada com Sucesso!";
        String texto = String.format(
                "Olá, %s!\n\nSua consulta com Dr(a). %s foi agendada com sucesso para %s.\n\nAtenciosamente,\nEquipe SGCC.",
                dto.getNomePaciente(),
                dto.getNomeMedico(),
                dto.getDataConsulta() // A data já vem formatada como String
        );
        enviarEmailSimples(dto.getEmailPaciente(), assunto, texto);
    }

    /**
     * Envia um e-mail de notificação quando uma consulta é cancelada.
     * Atende ao requisito RF3.2.
     *
     * @param dto O DTO contendo os detalhes da consulta cancelada.
     */
    public void enviarEmailCancelamento(ConsultaStatusDTO dto) {
        String assunto = "Sua Consulta foi Cancelada";
        String texto = String.format(
                "Olá, %s.\n\nInformamos que sua consulta com Dr(a). %s que estava agendada para %s foi cancelada.\n\nPara mais informações, entre em contato.\nAtenciosamente,\nEquipe SGCC.",
                dto.getNomePaciente(),
                dto.getNomeMedico(),
                dto.getDataConsulta()
        );
        enviarEmailSimples(dto.getEmailPaciente(), assunto, texto);
    }

    /**
     * Envia um e-mail de lembrete para uma consulta futura.
     * Atende ao requisito RF3.3 (a lógica de quando enviar é do Scheduler).
     *
     * @param dto O DTO contendo os detalhes da consulta a ser lembrada.
     */
    public void enviarEmailLembrete(ConsultaStatusDTO dto) {
        String assunto = "Lembrete de Consulta";
        String texto = String.format(
                "Olá, %s!\n\nEste é um lembrete sobre sua consulta com Dr(a). %s amanhã, %s.\n\nAtenciosamente,\nEquipe SGCC.",
                dto.getNomePaciente(),
                dto.getNomeMedico(),
                dto.getDataConsulta()
        );
        enviarEmailSimples(dto.getEmailPaciente(), assunto, texto);
    }

    /**
     * Método privado e genérico que realiza o envio do e-mail.
     *
     * @param para    O e-mail do destinatário.
     * @param assunto O assunto do e-mail.
     * @param texto   O corpo do e-mail.
     */
    private void enviarEmailSimples(String para, String assunto, String texto) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(remetente);
            mensagem.setTo(para);
            mensagem.setSubject(assunto);
            mensagem.setText(texto);

            emailSender.send(mensagem);

            System.out.println("E-mail de notificação enviado com sucesso para: " + para);
        } catch (Exception e) {
            // Imprime o erro no console para ajudar a depurar
            System.err.println("Erro ao tentar enviar e-mail para " + para + ": " + e.getMessage());
        }
    }
}