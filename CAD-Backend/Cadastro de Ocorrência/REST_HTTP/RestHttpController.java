package br.ufrn.imd.smartmetropolis.ciosp.controller.rest_http;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.CriarOcorrenciaResponse;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.ciosp.service.EventoService;

@RestController
@RequestMapping("http/eventos")
public class RestHttpController {
    @Autowired
    private EventoService service;

    @PostMapping("/ocorrencia")
    public ResponseEntity<CriarOcorrenciaResponse> criarOcorrencia(@RequestBody @Valid EventoDTO request){
        return ResponseEntity.ok(service.cadastrarOcorrencia(request));
    }
}
