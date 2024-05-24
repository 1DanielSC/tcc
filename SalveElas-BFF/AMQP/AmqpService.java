package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.amqp;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.ufrn.imd.smartmetropolis.cadrnappapi.model.UsuarioOcorrenciaStatus;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmqpService {
    
    @Value("${api.config.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.config.rabbitmq.routingkey.gerar-ocorrencia}")
    private String routingkeyGerarOcorrencia;

    private final RabbitTemplate rabbitTemplate;
    private final OcorrenciaService ocorrenciaService;

    
    public String gerarEventoOcorrencia(String cpfUsuario, Double latitude, Double longitude){
        EventoDTO evento = ocorrenciaService.prepararEvento(cpfUsuario, latitude, longitude);
        try {
            rabbitTemplate.convertAndSend(
                exchange,
                routingkeyGerarOcorrencia,
                evento
            );
            UsuarioOcorrenciaStatus ocorrenciaStatus = new UsuarioOcorrenciaStatus();
            ocorrenciaStatus.setCpfUsuario(cpfUsuario);
            ocorrenciaStatus.setStatus("Pendente");
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
