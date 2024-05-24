package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.amqp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartmetropolis.cadrnappapi.model.dto.OcorrenciaInputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("amqp/ocorrencias")
public class AmqpController {
    
    private final AmqpService service;

    @PostMapping("/evento")
    public ResponseEntity<String> gerarEventoOcorrencia(@Valid @RequestBody OcorrenciaInputDTO ocorrenciaInputDTO){
        return ResponseEntity.ok(
            service.gerarEventoOcorrencia(
                ocorrenciaInputDTO.getCpfUsuario(), 
                ocorrenciaInputDTO.getLatitude(), 
                ocorrenciaInputDTO.getLongitude()
            )
        );
    }
}
