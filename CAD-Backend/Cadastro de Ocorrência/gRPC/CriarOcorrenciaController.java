package br.ufrn.imd.smartmetropolis.ciosp.controller.grpc;

import br.com.cad.proto_content.CriarOcorrenciaServiceGrpc.CriarOcorrenciaServiceImplBase;
import br.ufrn.imd.smartmetropolis.ciosp.controller.grpc.mapper.OcorrenciaMapper;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.CriarOcorrenciaResponse;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.ciosp.service.EventoService;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cad.proto_content.CriarOcorrenciaRequest_gRCP;
import br.com.cad.proto_content.OcorrenciaResponse_gRCP;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CriarOcorrenciaController extends CriarOcorrenciaServiceImplBase {

    @Autowired
    private EventoService eventoService;

    @Override
    public void criarOcorrencia(CriarOcorrenciaRequest_gRCP request,
            StreamObserver<OcorrenciaResponse_gRCP> responseObserver) {

        EventoDTO eventoDTO = OcorrenciaMapper.toEventoDTO(request);

        CriarOcorrenciaResponse ocorrencia = eventoService.cadastrarOcorrencia(eventoDTO);

        responseObserver.onNext(OcorrenciaMapper.toOcorrenciaResponse_gRPC(ocorrencia));
        responseObserver.onCompleted();
    }
    
}
