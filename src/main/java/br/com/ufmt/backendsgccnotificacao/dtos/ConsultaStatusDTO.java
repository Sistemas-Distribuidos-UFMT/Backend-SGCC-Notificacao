package br.com.ufmt.backendsgccnotificacao.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaStatusDTO implements Serializable {
    private String emailPaciente;
    private String situacao;
    private String nomePaciente;
    private String nomeMedico;
    private String dataConsulta; // Recebido como String
}