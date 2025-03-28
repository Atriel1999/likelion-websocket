package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class StompMessagingService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public StompMessagingService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendToTopic(ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
    }


    public void sendToUser(String username, ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/private", chatMessage);
    }

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        ChatMessage welcomeMessage = ChatMessage.builder()
                .sender("System")
                .content(
                        String.format("""
                        %s 님이 들어왔습니다.
                        """, chatMessage.getSender()))
                .type(ChatMessage.MessageType.JOIN)
                .build();

        return welcomeMessage;
    }

    public ChatMessage createLeaveMessage(ChatMessage chatMessage) {
        ChatMessage leaveMessage = ChatMessage.builder()
                .sender("System")
                .content(
                        String.format("""
                        %s 님이 퇴장하셨습니다.
                        """, chatMessage.getSender()))
                .type(ChatMessage.MessageType.LEAVE)
                .build();

        return leaveMessage;
    }
}
