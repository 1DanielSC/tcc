package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.grpc;

import br.com.cad.proto_content.CriarOcorrenciaRequest_gRCP;
import br.com.cad.proto_content.EnderecoDTO_gRPC;
import br.com.cad.proto_content.EnderecoOpcionalDTO_gRPC;
import br.com.cad.proto_content.OcorrenciaResponse_gRCP;
import br.com.cad.proto_content.EnderecoOpcionalDTO_gRPC.EnumTipoEnderecoOpcional_gRPC;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.AgenciaDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.CadResponseDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.EnderecoOpcionalDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.SubtipoOcorrenciaDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.TipoOcorrenciaDTO;


public class GrpcMapper {

    public static EnumTipoEnderecoOpcional_gRPC getTipoEndereco(EnderecoOpcionalDTO endereco){
        if(endereco == null){
            return null;
        }

        switch (endereco.getTipoEndereco()) {
            case ID: return EnumTipoEnderecoOpcional_gRPC.ID;
            case NOME: return EnumTipoEnderecoOpcional_gRPC.NOME;
            case OSM: return EnumTipoEnderecoOpcional_gRPC.OSM;
            default: return null;
        }
    }

    public static CriarOcorrenciaRequest_gRCP toGrpcRequest(EventoDTO evento){

        EnumTipoEnderecoOpcional_gRPC tipoMunicipio = getTipoEndereco(evento.getEndereco().getMunicipio());
        EnumTipoEnderecoOpcional_gRPC tipoBairro = getTipoEndereco(evento.getEndereco().getBairro());
        EnumTipoEnderecoOpcional_gRPC tipoLogradouro = getTipoEndereco(evento.getEndereco().getLogradouro());
        EnumTipoEnderecoOpcional_gRPC tipoLocalidade = getTipoEndereco(evento.getEndereco().getLocalidade());
        EnumTipoEnderecoOpcional_gRPC tipoNumero = getTipoEndereco(evento.getEndereco().getNumero());

        return CriarOcorrenciaRequest_gRCP
        .newBuilder()
        .setTipoOcorrencia(evento.getTipoOcorrencia())
        .setSubtipoOcorrencia(evento.getSubTipoOcorrencia())
        .addAllGrupos(evento.getGrupos())
        .addAllAgencias(evento.getAgencias())
        .addAllDetalhes(evento.getDetalhes())
        .setReferencia(evento.getReferencia())
        .setNomeSolicitante(evento.getNomeSolicitante())
        .setEndereco(
            EnderecoDTO_gRPC.newBuilder()
            .setMunicipio(
                EnderecoOpcionalDTO_gRPC.newBuilder()
                .setId(evento.getEndereco().getMunicipio().getId())
                //.setNome(evento.getEndereco().getMunicipio().getNome())
                .setTipoEndereco(tipoMunicipio)
                .build()
            )
            .setBairro(
                EnderecoOpcionalDTO_gRPC.newBuilder()
                .setId(evento.getEndereco().getBairro().getId())
                //.setNome(evento.getEndereco().getBairro().getNome())
                .setTipoEndereco(tipoBairro)
                .build()
            )
            .setLogradouro(
                EnderecoOpcionalDTO_gRPC.newBuilder()
                .setId(evento.getEndereco().getLogradouro().getId())
                // .setNome(evento.getEndereco().getLogradouro().getNome())
                .setTipoEndereco(tipoLogradouro)
                .build()
            )
            .setLocalidade(
                EnderecoOpcionalDTO_gRPC.newBuilder()
                // .setId(evento.getEndereco().getLocalidade().getId())
                .setNome(evento.getEndereco().getLocalidade().getNome())
                .setTipoEndereco(tipoLocalidade)
                .build()
            )
            .setNumero(
                EnderecoOpcionalDTO_gRPC.newBuilder()
                // .setId(evento.getEndereco().getNumero().getId())
                .setNome(evento.getEndereco().getNumero().getNome())
                .setTipoEndereco(tipoNumero)
                .build()
            )
            .setLatitude(evento.getEndereco().getLatitude())
            .setLongitude(evento.getEndereco().getLongitude())
        ).build();
    }

    public static CadResponseDTO fromCriarOcorrenciaResponse_gRCP(OcorrenciaResponse_gRCP dto){
        TipoOcorrenciaDTO tipo = new TipoOcorrenciaDTO();
        tipo.setCodigo(dto.getTipoOcorrencia().getCodigo());
        tipo.setNome(dto.getTipoOcorrencia().getNome());
        tipo.setPrioridade(dto.getTipoOcorrencia().getPrioridade());

        SubtipoOcorrenciaDTO subtipo = new SubtipoOcorrenciaDTO();
        subtipo.setCodigo(dto.getTipoOcorrencia().getCodigo());
        subtipo.setNome(dto.getTipoOcorrencia().getNome());
        subtipo.setTipoOcorrenciaId(473);

        AgenciaDTO agencia = new AgenciaDTO();
        agencia.setId(dto.getAgencia().getId());
        agencia.setNome(dto.getAgencia().getNome());
        agencia.setOrgao(dto.getAgencia().getOrgao());
        agencia.setAbreviacao(dto.getAgencia().getAbreviacao());
        
        return CadResponseDTO.builder()
        .id(dto.getId())
        .protocolo(dto.getProtocolo())
        .status(dto.getStatus())
        .idCentro("Natal")
        .agencia(agencia)
        .tipoOcorrencia(tipo)
        .subtipoOcorrenciaDTO(subtipo)
        // Endereco
        .municipio(dto.getMunicipio())
        .bairro(dto.getBairro())
        .logradouro(dto.getLogradouro())
        .localidade(dto.getLocalidade())
        .referencia(dto.getReferencia())
        .numero(dto.getNumero())
        .latitude(dto.getLatitude())
        .longitude(dto.getLongitude())

        .nomeSolicitante(dto.getNomeSolicitante())
        .telefoneSolicitante(dto.getTelefoneSolicitante())
        .build();
        
    }

}
