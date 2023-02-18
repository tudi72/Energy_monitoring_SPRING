package ro.tuc.ds2020.rabbitmq.controller;
import java.util.Timer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import ro.tuc.ds2020.rabbitmq.domain.Message;
import ro.tuc.ds2020.rabbitmq.handler.MessageSender;


@Slf4j
@Controller
public class WebSocketController {

    private final RabbitTemplate rabbitTemplate;

    WebSocketController(final RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @MessageMapping("/chat.newDevice")
    public Message newDevice(@Payload Message message){
        log.info("[chat.newDevice] : received new client ...{}",message.toString());
        new Timer().schedule(new MessageSender(this.rabbitTemplate,message), 0,60_000L);
        return message;
    }
}   

