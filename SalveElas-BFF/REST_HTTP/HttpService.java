package br.ufrn.imd.smartmetropolis.cadrnappapi.controller.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.ufrn.imd.smartmetropolis.cadrnappapi.exception.ValidationException;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.Centro;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.CadResponseDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.model.ciosp.dtos.EventoDTO;
import br.ufrn.imd.smartmetropolis.cadrnappapi.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HttpService {
    
    private final RestTemplateBuilder restTemplateBuilder;
    private final OcorrenciaService ocorrenciaService;

    public CadResponseDTO gerarOcorrenciaRestHttp(String cpfUsuario, Double latitude, Double longitude){

        EventoDTO evento = ocorrenciaService.prepararEvento(cpfUsuario, latitude, longitude);
        Centro centro = ocorrenciaService.getCentro();
        String centroId = centro.getCentroId();

        RestTemplate ciospApi = restTemplateBuilder
                .defaultHeader("Authorization", "Bearer " + centro.getToken())
                .rootUri(centro.getUrl())
                .build();

        try {            
            HttpEntity<EventoDTO> request = new HttpEntity<>(evento);

            ResponseEntity<CadResponseDTO> response = ciospApi.exchange(
                "/eventos/ocorrencia",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                });
                
            Objects.requireNonNull(response.getBody()).setIdCentro(centroId);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            throw new ValidationException(e.getResponseBodyAsString());
        } 
    }
}
