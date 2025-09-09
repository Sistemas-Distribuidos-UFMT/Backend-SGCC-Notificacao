package br.com.ufmt.backendsgccnotificacao.dtos;

import java.time.LocalDateTime;

// Usaremos Lombok para gerar getters, setters, etc.
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {

    private TipoNotificacao tipo;
    private String destinatarioEmail;
    private String nomePaciente;
    private LocalDateTime dataConsulta;
    private String nomeMedico;

}