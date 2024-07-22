package org.yusufakbas.whispersafe.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.yusufakbas.whispersafe.dto.MessageDto;

@Controller
public class RealTimeChat {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public RealTimeChat(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public MessageDto receiveMessage(@Payload MessageDto message) {
        if (message.getChatId() == null) {
            System.err.println("Received message with null chat: " + message);
        } else {
            System.out.println("Received message with chat ID: " + message.getChatId());
        }

        try {
            simpMessagingTemplate.convertAndSend("/topic/" + message.getChatId().toString(), message);
        } catch (Exception e) {
            // Log or handle the exception
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
        return message;
    }
}