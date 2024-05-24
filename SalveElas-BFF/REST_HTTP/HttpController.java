package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.CadResponseDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.dto.OcorrenciaInputDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("http/ocorrencias")
public class HttpController {

    private final OcorrenciaService ocorrenciaService;

    public ResponseEntity<CadResponseDTO> gerarOcorrencia(@Valid @RequestBody OcorrenciaInputDTO ocorrenciaInputDTO) {
        CadResponseDTO ocorrencias = ocorrenciaService.gerarOcorrenciaRestHttp(
            ocorrenciaInputDTO.getCpfUsuario(), 
            ocorrenciaInputDTO.getLatitude(), 
            ocorrenciaInputDTO.getLongitude()
        );
        return ResponseEntity.ok(ocorrencias);
    }
}
