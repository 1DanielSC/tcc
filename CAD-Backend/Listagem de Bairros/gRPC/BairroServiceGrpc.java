package br.ufrn.imd.smartmetropolis.ciosp.controller.grpc;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cad.proto_content.BairroListResponse;
import br.com.cad.proto_content.BairroResponse;
import br.com.cad.proto_content.MunicipioResponse;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.BairroFindAllDTO;
import br.ufrn.imd.smartmetropolis.ciosp.repository.BairroRepository;

@Service
public class BairroServiceGrpc {

     @Autowired
    private BairroRepository repository;

    List<BairroFindAllDTO> bairros = null;
    BairroListResponse.Builder responseBuilder = BairroListResponse.newBuilder();
    BairroListResponse bairrosGrpc = null;
    @PostConstruct
    public void init(){
        this.bairros = repository.findAllByOrigemEnderecoNotOSM().subList(0, 300);

        this.bairros.forEach(bairro -> {
            BairroResponse bairroResponse = BairroResponse.newBuilder()
                .setId(bairro.getId())
                .setNome(bairro.getNome())
                .setOrigemEndereco(bairro.getOrigemEndereco().name())
                .setMunicipio(bairro.getMunicipio() != null ?
                    MunicipioResponse.newBuilder()
                        .setId(bairro.getMunicipio().getId())
                        .setNome(bairro.getMunicipio().getNome())
                        .setOrigemEndereco(bairro.getMunicipio().getOrigemEndereco().name())
                        .build()
                    : null
                )
                .build();
            
            responseBuilder.addBairros(bairroResponse);
        });
        this.bairrosGrpc = responseBuilder.build();
    }

    public List<BairroResponse> getAllBairrosGrpc() {
        return this.bairrosGrpc.getBairrosList();
    }
}
