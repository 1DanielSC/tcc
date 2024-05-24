package br.ufrn.imd.smartmetropolis.ciosp.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.EventoDTO;

@Component
public class RabbitMQSalveElasListener {

    @Autowired
    private EventoService eventoService;

    @RabbitListener(
        queues = "${app.salveelas.rabbitmq.queue.gerar-ocorrencia}", 
        containerFactory = "simpleConnectionFactorySalveElas",
        concurrency = "200"
    )
    @Transactional
    public void receiveSalveElasOcorrencia(EventoDTO payload){
        try {
            eventoService.salvar(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
