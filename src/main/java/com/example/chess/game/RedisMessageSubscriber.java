package com.example.chess.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisMessageSubscriber implements MessageListener {

    public static List<String> messageList = new ArrayList<>();
    private SimpMessagingTemplate simpMessagingTemplate;

    public RedisMessageSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("Message received : " + message.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("!!!!!!!!!");

        InternalMessage internalMessage = objectMapper.readValue(message.toString(), InternalMessage.class);
        System.out.println("!!!!!!!!!");

        simpMessagingTemplate.convertAndSend(internalMessage.getDestination(), internalMessage.getData());
    }

}