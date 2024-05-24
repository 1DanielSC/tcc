package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.grpc;

import org.springframework.stereotype.Service;

import br.com.cad.proto_content.OcorrenciaResponse_gRCP;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadGrpcService {

    private final OcorrenciaService ocorrenciaService;
    private final GrpcClient grpcClient;

    public OcorrenciaResponse_gRCP gerarOcorrenciaGrpc(String cpfUsuario, Double latitude, Double longitude){
        EventoDTO evento = ocorrenciaService.prepararEvento(cpfUsuario, latitude, longitude);
        return grpcRequest(evento);
    }

    private OcorrenciaResponse_gRCP grpcRequest(EventoDTO evento){
        return grpcClient.chamarCadBackend(evento);
    }
    
}
