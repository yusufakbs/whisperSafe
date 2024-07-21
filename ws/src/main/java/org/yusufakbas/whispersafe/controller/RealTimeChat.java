package org.yusufakbas.whispersafe.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.yusufakbas.whispersafe.model.Message;

@Controller
public class RealTimeChat {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public RealTimeChat(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message) {
        try {
            simpMessagingTemplate.convertAndSend("/group/" + message.getChat().getId().toString(), message);
        } catch (Exception e) {
            // Log or handle the exception
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
        return message;
    }

}