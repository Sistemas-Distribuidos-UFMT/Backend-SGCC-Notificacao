package br.com.ufmt.backendsgccnotificacao.scheduler;

import br.com.ufmt.backendsgccnotificacao.dtos.ConsultaStatusDTO;
import br.com.ufmt.backendsgccnotificacao.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Component
public class LembreteScheduler {

    @Autowired
    private LembreteCacheService lembreteCacheService;

    @Autowired
    private EmailService emailService;

    // O formato da data que esperamos receber do Serviço de Agendamento
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    @Scheduled(fixedRate = 300000) // Roda a cada 5 minutos para testes
    public void verificarCacheParaLembretes() {
        System.out.println("--- ROTINA AGENDADA: Verificando cache para envio de lembretes... ---");

        Collection<ConsultaStatusDTO> consultas = lembreteCacheService.getConsultasParaLembrete();
        LocalDateTime agora = LocalDateTime.now();

        for (ConsultaStatusDTO dto : consultas) {
            try {
                LocalDateTime dataConsulta = LocalDateTime.parse(dto.getDataConsulta(), formatter);

                // Verifica se a consulta está nas próximas 24 horas
                if (dataConsulta.isAfter(agora) && dataConsulta.isBefore(agora.plusHours(24))) {
                    System.out.println("Lembrete para consulta [" + dto.getEmailPaciente() + " em " + dto.getDataConsulta() + "] está na janela de 24h. Enviando e-mail...");
                    emailService.enviarEmailLembrete(dto);
                    lembreteCacheService.removerConsultaDoLembrete(dto);
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar lembrete para DTO: " + dto.toString() + " - Erro: " + e.getMessage());
                // Em um caso real, poderíamos mover esta mensagem para uma fila de erro.
                lembreteCacheService.removerConsultaDoLembrete(dto);
            }
        }
        System.out.println("--- FIM DA ROTINA AGENDADA ---");
    }
}