package br.ufrn.imd.smartmetropolis.ciosp.controller.rest_http;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.BairroFindAllDTO;
import br.ufrn.imd.smartmetropolis.ciosp.repository.BairroRepository;

@Service
public class BairroServiceHttp {
    
    @Autowired
    private BairroRepository repository;

    List<BairroFindAllDTO> bairros = null;
    @PostConstruct
    public void init(){
        this.bairros = repository.findAllByOrigemEnderecoNotOSM().subList(0, 300);
    }

    public List<BairroFindAllDTO> findAllBairros(){
        return this.bairros;
    }
}
