package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.grpc;

import br.com.cad.proto_content.GerarOcorrenciaRequestBFF_gRPC;
import br.com.cad.proto_content.GerarOcorrenciaServiceGrpc.GerarOcorrenciaServiceImplBase;
import br.com.cad.proto_content.OcorrenciaResponse_gRCP;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GerarOcorrenciaControllerGrpc extends GerarOcorrenciaServiceImplBase{

    private final CadGrpcService grpcService;

    @Override
    public void gerarOcorrenciaBFF(GerarOcorrenciaRequestBFF_gRPC request,
            StreamObserver<OcorrenciaResponse_gRCP> responseObserver) {
        

        OcorrenciaResponse_gRCP response = 
        grpcService.gerarOcorrenciaGrpc(
            request.getCpfUsuario(), 
            request.getLatitude(), 
            request.getLongitude()
        );
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
