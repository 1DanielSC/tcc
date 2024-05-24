package br.ufrn.imd.smartmetropolis.ciosp.controller.grpc.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.cad.proto_content.AgenciaDTO_gRPC;
import br.com.cad.proto_content.CriarOcorrenciaRequest_gRCP;
import br.com.cad.proto_content.OcorrenciaResponse_gRCP;
import br.com.cad.proto_content.SubtipoOcorrenciaDTO_gRPC;
import br.com.cad.proto_content.TipoOcorrenciaDTO_gRPC;
import br.ufrn.imd.smartmetropolis.ciosp.model.EventoDetalhe;
import br.ufrn.imd.smartmetropolis.ciosp.model.Ocorrencia;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.AgenciaDTO2;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.CriarOcorrenciaResponse;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.EnderecoDTO;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.EnderecoOpcionalDTO;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.LigacaoDTO;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.SubtipoOcorrenciaDTO;
import br.ufrn.imd.smartmetropolis.ciosp.model.dtos.TipoOcorrenciaDTO;
import br.ufrn.imd.smartmetropolis.ciosp.model.enums.EnumTipoEnderecoOpcional;

public class OcorrenciaMapper {

    private static EnumTipoEnderecoOpcional getTipoEndereco(String tipo){
        if(tipo.equals("ID"))
            return EnumTipoEnderecoOpcional.ID;
        else if(tipo.equals("NOME"))
            return EnumTipoEnderecoOpcional.NOME;
        else
            return EnumTipoEnderecoOpcional.OSM;
    }
    
    public static EventoDTO toEventoDTO(CriarOcorrenciaRequest_gRCP request){
        EventoDTO evento = new EventoDTO();

        evento.setNomeSolicitante(request.getNomeSolicitante());
        evento.setReferencia(request.getReferencia());
        evento.setTipoOcorrencia(request.getTipoOcorrencia());
        evento.setSubTipoOcorrencia(request.getSubtipoOcorrencia());
        evento.setGrupos(request.getGruposList());
        evento.setAgencias(request.getAgenciasList());

        LigacaoDTO ligacaoDTO = new LigacaoDTO();
        ligacaoDTO.setHoraLigacaoCentral(request.getLigacao().getHoraLigacaoCentral());
        ligacaoDTO.setIdLigacaoCentral(request.getLigacao().getIdLigacaoCentral());
        ligacaoDTO.setTelefoneSolicitante(request.getLigacao().getTelefoneSolicitante());
        evento.setLigacao(ligacaoDTO);

        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setLatitude(request.getEndereco().getLatitude());
        endereco.setLongitude(request.getEndereco().getLongitude());

        EnderecoOpcionalDTO municipio = new EnderecoOpcionalDTO();
        municipio.setId(request.getEndereco().getMunicipio().getId());
        municipio.setNome(request.getEndereco().getMunicipio().getNome());
        String tipo = request.getEndereco().getMunicipio().getTipoEndereco().name();        
        municipio.setTipoEndereco(getTipoEndereco(tipo));
            
        endereco.setMunicipio(municipio);

        EnderecoOpcionalDTO bairro = new EnderecoOpcionalDTO();
        bairro.setId(request.getEndereco().getBairro().getId());
        bairro.setNome(request.getEndereco().getBairro().getNome());
        String tipoBairro = request.getEndereco().getBairro().getTipoEndereco().name();
        bairro.setTipoEndereco(getTipoEndereco(tipoBairro));
        endereco.setBairro(bairro);

        EnderecoOpcionalDTO logradouro = new EnderecoOpcionalDTO();
        logradouro.setId(request.getEndereco().getLogradouro().getId());
        logradouro.setNome(request.getEndereco().getLogradouro().getNome());
        String tipoLogradouro = request.getEndereco().getLogradouro().getTipoEndereco().name();
        logradouro.setTipoEndereco(getTipoEndereco(tipoLogradouro));
        endereco.setLogradouro(logradouro);

        EnderecoOpcionalDTO localidade = new EnderecoOpcionalDTO();
        localidade.setId(request.getEndereco().getLocalidade().getId());
        localidade.setNome(request.getEndereco().getLocalidade().getNome());
        String tipoLocalidade = request.getEndereco().getLocalidade().getTipoEndereco().name();
        localidade.setTipoEndereco(getTipoEndereco(tipoLocalidade));
        endereco.setLocalidade(localidade);

        EnderecoOpcionalDTO numero = new EnderecoOpcionalDTO();
        numero.setId(request.getEndereco().getNumero().getId());
        numero.setNome(request.getEndereco().getNumero().getNome());
        String tipoNumero = request.getEndereco().getNumero().getTipoEndereco().name();
        numero.setTipoEndereco(getTipoEndereco(tipoNumero));
        endereco.setNumero(numero);

        evento.setEndereco(endereco);


        List<EventoDetalhe> detalhes = new ArrayList<>();

        request
        .getDetalhesList()
        .stream()
        .forEach(detalhe -> {
            EventoDetalhe eventoDetalhe = new EventoDetalhe();
            eventoDetalhe.setDescricao(detalhe);
            detalhes.add(eventoDetalhe);
        });
        evento.setDetalhes(detalhes);

        return evento;
    }

    public static CriarOcorrenciaResponse toOcorrenciaResponse(Ocorrencia ocorrencia){

        AgenciaDTO2 agencia = new AgenciaDTO2();
        agencia.setId(ocorrencia.getAgencia().getId());
        agencia.setNome(ocorrencia.getAgencia().getNome());
        agencia.setOrgao(ocorrencia.getAgencia().getOrgao());
        agencia.setAbreviacao(ocorrencia.getAgencia().getAbreviacao());

        TipoOcorrenciaDTO tipoOcorrencia = new TipoOcorrenciaDTO();
        tipoOcorrencia.setNome(ocorrencia.getTipoOcorrencia().getNome());
        tipoOcorrencia.setCodigo(ocorrencia.getTipoOcorrencia().getCodigo());


        SubtipoOcorrenciaDTO subtipoOcorrencia = new SubtipoOcorrenciaDTO();
        subtipoOcorrencia.setTipoOcorrenciaId(
            ocorrencia.getSubtipoOcorrencia().getTipoOcorrencia().getId()
        );
        subtipoOcorrencia.setNome(ocorrencia.getSubtipoOcorrencia().getSubtipo());
        subtipoOcorrencia.setCodigo(ocorrencia.getSubtipoOcorrencia().getCodigo());
        
        CriarOcorrenciaResponse response = 
            CriarOcorrenciaResponse.builder()

                // .id(ocorrencia.getId())
                .id(1)
                .protocolo(ocorrencia.getProtocolo())
                .agencia(agencia)
                .status(ocorrencia.getStatus().getStatusOcorrencia())

                .municipio(ocorrencia.getEvento().getEndereco().getMunicipio().getNome())
                .bairro(ocorrencia.getEvento().getEndereco().getBairro().getNome())
                .logradouro(ocorrencia.getEvento().getEndereco().getLogradouro().getNome())
                .localidade(ocorrencia.getEvento().getEndereco().getLocalidade().getNome())
                .numero(ocorrencia.getEvento().getEndereco().getNumero())
                .referencia(ocorrencia.getEvento().getReferencia())

                .tipoOcorrencia(tipoOcorrencia)
                .subtipoOcorrenciaDTO(subtipoOcorrencia)

                .latitude(ocorrencia.getEvento().getEndereco().getLatitude())
                .longitude(ocorrencia.getEvento().getEndereco().getLongitude())

                .nomeSolicitante(ocorrencia.getEvento().getNomeSolicitante())
                .telefoneSolicitante(ocorrencia.getEvento().getTelefoneSolicitante())
                .build();
        return response;
    }

    public static OcorrenciaResponse_gRCP toOcorrenciaResponse_gRPC(CriarOcorrenciaResponse ocorrencia){
        return OcorrenciaResponse_gRCP.newBuilder()
        .setId(ocorrencia.getId())
        .setProtocolo(ocorrencia.getProtocolo())
        .setStatus(ocorrencia.getStatus())

        .setMunicipio(ocorrencia.getMunicipio())
        .setBairro(ocorrencia.getBairro())
        .setLogradouro(ocorrencia.getLogradouro())
        .setLocalidade(ocorrencia.getLocalidade())
        .setNumero(ocorrencia.getNumero())
        .setReferencia(ocorrencia.getReferencia())

        .setLatitude(ocorrencia.getLatitude())
        .setLongitude(ocorrencia.getLongitude())

        .setAgencia(
            AgenciaDTO_gRPC.newBuilder()
            .setId(ocorrencia.getAgencia().getId())
            .setNome(ocorrencia.getAgencia().getNome())
            .setOrgao(ocorrencia.getAgencia().getOrgao())
            .setAbreviacao(ocorrencia.getAgencia().getAbreviacao())
            .build()
        )

        .setTipoOcorrencia(
            TipoOcorrenciaDTO_gRPC.newBuilder()
                .setCodigo(ocorrencia.getTipoOcorrencia().getCodigo())
                .setNome(ocorrencia.getTipoOcorrencia().getNome())
                //.setPrioridade(ocorrencia.getTipoOcorrencia().getPrioridade())
                .build()
        )

        .setSubtipoOcorrencia(
            SubtipoOcorrenciaDTO_gRPC.newBuilder()
                .setNome(ocorrencia.getSubtipoOcorrenciaDTO().getNome())
                .setIdTipoOcorrencia(ocorrencia.getSubtipoOcorrenciaDTO().getTipoOcorrenciaId())
                .setCodigo(ocorrencia.getSubtipoOcorrenciaDTO().getCodigo())
                .build()
        )   

        .setNomeSolicitante(ocorrencia.getNomeSolicitante())
        .setTelefoneSolicitante(ocorrencia.getTelefoneSolicitante())
        .build();
    }
}
