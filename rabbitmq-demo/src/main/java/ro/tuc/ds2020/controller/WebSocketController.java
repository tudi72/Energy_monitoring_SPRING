package ro.tuc.ds2020.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import ro.tuc.ds2020.domain.WebSocketMessage;

@Controller
public class WebSocketController {
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/ds2020")
    public WebSocketMessage sendMessage(@Payload WebSocketMessage message){
        return message;
    }


    @MessageMapping("/chat.newUser")
    @SendTo("/topic/ds2020")
    public WebSocketMessage newUser(@Payload WebSocketMessage message, SimpMessageHeaderAccessor accessor){
        accessor.getSessionAttributes().put("username",message);
        return message;
    }
}
