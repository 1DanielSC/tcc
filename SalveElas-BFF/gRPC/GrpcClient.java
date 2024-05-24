package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.grpc;

import java.util.concurrent.TimeUnit;
import java.time.Duration;

import org.springframework.stereotype.Service;

import br.com.cad.proto_content.CriarOcorrenciaRequest_gRCP;
import br.com.cad.proto_content.CriarOcorrenciaServiceGrpc;
import br.com.cad.proto_content.OcorrenciaResponse_gRCP;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.EventoDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class GrpcClient {
    private ManagedChannel channel;
    private CriarOcorrenciaServiceGrpc.CriarOcorrenciaServiceBlockingStub stub;

    public GrpcClient(){
        this.channel = ManagedChannelBuilder.forAddress("backend", 9090)
          .usePlaintext()
          .build();
        this.stub = CriarOcorrenciaServiceGrpc.newBlockingStub(channel);
    }

    public OcorrenciaResponse_gRCP chamarCadBackend(EventoDTO evento){
        CriarOcorrenciaRequest_gRCP request = GrpcMapper.toGrpcRequest(evento);

        OcorrenciaResponse_gRCP response = stub
        .withDeadlineAfter(Duration.ofSeconds(30).getSeconds(), TimeUnit.SECONDS)
        .criarOcorrencia(
            CriarOcorrenciaRequest_gRCP.newBuilder()
            .addAllAgencias(request.getAgenciasList())
            .addAllDetalhes(request.getDetalhesList())
            .addAllGrupos(request.getGruposList())
            .setEndereco(request.getEndereco())
            .setLigacao(request.getLigacao())
            .setNomeSolicitante(request.getNomeSolicitante())
            .setReferencia(request.getReferencia())
            .setTipoOcorrencia(request.getTipoOcorrencia())
            .setSubtipoOcorrencia(request.getSubtipoOcorrencia())
            .build()
        );

        return response;
    }
}
