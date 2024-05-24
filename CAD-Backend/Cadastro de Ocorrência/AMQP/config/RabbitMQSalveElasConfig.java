package br.ufrn.imd.smartmetropolis.ciosp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@EnableRabbit
@Configuration
public class RabbitMQSalveElasConfig implements RabbitListenerConfigurer {

    //@Value("${app.salveelas.rabbitmq.queue}")
    @Value("${app.salveelas.rabbitmq.queue.gerar-ocorrencia}")
    private String queueName;

    @Value("${app.salveelas.rabbitmq.exchange}")
    private String exchange;

    //@Value("${app.salveelas.rabbitmq.routingkey}")
    @Value("${app.salveelas.rabbitmq.routingkey.gerar-ocorrencia}")
    private String routingKey;

    @Value("${spring.rabbitmq.salveelas.host}")
    private String host;

    @Value("${spring.rabbitmq.salveelas.port}")
    private int port;

    @Value("${spring.rabbitmq.salveelas.username}")
    private String username;

    @Value("${spring.rabbitmq.salveelas.password}")
    private String password;

    private final MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    public RabbitMQSalveElasConfig(MappingJackson2HttpMessageConverter springMvcJacksonConverter) {
        this.springMvcJacksonConverter = springMvcJacksonConverter;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter(springMvcJacksonConverter.getObjectMapper());
    }

    @Bean
    DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    private CachingConnectionFactory createConnectionFactory() {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean(name = "connectionFactorySalveElas")
    public ConnectionFactory connectionFactorySalveElas() {
       return createConnectionFactory();
    }

    @Bean(name = "simpleConnectionFactorySalveElas")
    public SimpleRabbitListenerContainerFactory simpleConnectionFactorySalveElas() {
        SimpleRabbitListenerContainerFactory connectionFactory = new SimpleRabbitListenerContainerFactory();
        connectionFactory.setConnectionFactory(createConnectionFactory());
        connectionFactory.setMessageConverter(this.producerJackson2MessageConverter());
        return connectionFactory;
    }

    @Bean(name = "rabbitTemplateSalveElas")
    public RabbitTemplate rabbitTemplateSalveElas(@Qualifier("connectionFactorySalveElas") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(handlerMethodFactory());
    }

    private Queue salveElasQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    private TopicExchange salveElasExchange() {
        return new TopicExchange(exchange);
    }

    private Binding salveElasBinding() {
        return BindingBuilder.bind(salveElasQueue())
                .to(salveElasExchange())
                .with(routingKey);
    }

    @Bean
    public RabbitAdmin rabbitAdminSalveElas(@Qualifier("connectionFactorySalveElas") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        rabbitAdmin.declareQueue(salveElasQueue());
        rabbitAdmin.declareBinding(salveElasBinding());
        return rabbitAdmin;
    }
}
