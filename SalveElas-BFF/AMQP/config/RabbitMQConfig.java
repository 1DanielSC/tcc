package br.ufrn.imd.smartmetropolis.cadrnappapi.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class RabbitMQConfig {
    private final MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    public RabbitMQConfig(MappingJackson2HttpMessageConverter springMvcJacksonConverter) {
        this.springMvcJacksonConverter = springMvcJacksonConverter;
    }

    @Bean
    Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter(springMvcJacksonConverter.getObjectMapper());
    }
}
