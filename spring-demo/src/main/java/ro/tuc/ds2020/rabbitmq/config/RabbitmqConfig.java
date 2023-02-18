package ro.tuc.ds2020.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@Configuration
@EnableScheduling
public class RabbitmqConfig {

	public static final String EXCHANGE_NAME = "tudorita_exchange";
	public static final String DEFAULT_PARSER_QUEUE = "tudorita_queue";
	public static final String ROUTING_KEY = "tudorita_routingkey";

   @Bean
   Queue defaultParserQueue(){
    return new Queue(DEFAULT_PARSER_QUEUE);
   }
   
   @Bean
   TopicExchange TopicExchange(){
    return new TopicExchange(EXCHANGE_NAME);
   }
   
   @Bean
   Binding binding(){
    return BindingBuilder.bind(defaultParserQueue()).to(TopicExchange()).with(ROUTING_KEY);
   }

   @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(mapper);
    }

   @Bean
   public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        log.info("[RabbitmqConfig.template] : establishing connection ...");
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
   }
}