package br.com.ufmt.backendsgccnotificacao.scheduler;

import br.com.ufmt.backendsgccnotificacao.dtos.ConsultaStatusDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LembreteCacheService {

    private final ConcurrentHashMap<String, ConsultaStatusDTO> consultasParaLembrete = new ConcurrentHashMap<>();

    // Cria uma chave única combinando e-mail e data da consulta
    private String gerarChaveCache(ConsultaStatusDTO dto) {
        return dto.getEmailPaciente() + "::" + dto.getDataConsulta();
    }

    public void adicionarConsultaParaLembrete(ConsultaStatusDTO dto) {
        String chave = gerarChaveCache(dto);
        System.out.println("Adicionando consulta ao cache com chave: " + chave);
        consultasParaLembrete.put(chave, dto);
    }

    public void removerConsultaDoLembrete(ConsultaStatusDTO dto) {
        String chave = gerarChaveCache(dto);
        ConsultaStatusDTO removido = consultasParaLembrete.remove(chave);
        if (removido != null) {
            System.out.println("Removendo consulta do cache com chave: " + chave);
        }
    }

    public Collection<ConsultaStatusDTO> getConsultasParaLembrete() {
        return consultasParaLembrete.values();
    }
}