package br.ufrn.imd.smartmetropolis.ciosp.controller.rest_http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.BairroFindAllDTO;
import br.ufrn.imd.smartmetropolis.ciosp.service.BairroService;

@RestController
@RequestMapping("http/bairros")
public class RestHttpBairrosController {
    
    @Autowired
    private BairroService service;

    @GetMapping
    public ResponseEntity<List<BairroFindAllDTO>> findAll(){
        return ResponseEntity.ok(service.findAllBairros());
    }
}
