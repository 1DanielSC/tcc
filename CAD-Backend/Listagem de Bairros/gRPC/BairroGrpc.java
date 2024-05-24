package br.ufrn.imd.smartmetropolis.ciosp.controller.grpc;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.imd.smartmetropolis.ciosp.service.BairroService;
import br.com.cad.proto_content.BairroRequest;
import br.com.cad.proto_content.BairroResponse;
import br.com.cad.proto_content.BairroGrpcServiceGrpc.BairroGrpcServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BairroGrpc extends BairroGrpcServiceImplBase  {

    @Autowired
    private BairroService service;

    @Override
    public void listarBairrosStreaming(BairroRequest request, StreamObserver<BairroResponse> responseObserver) {
        service.getAllBairrosGrpc().forEach(bairro -> {
            responseObserver.onNext(bairro);
        });
        responseObserver.onCompleted();        
    }
    
}
