package br.com.ufmt.backendsgccnotificacao.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID; // IMPORTAR

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {

    private UUID consultaId; // <-- CAMPO ADICIONADO
    private TipoNotificacao tipo;
    private String destinatarioEmail;
    private String nomePaciente;
    private LocalDateTime dataConsulta;
    private String nomeMedico;

}