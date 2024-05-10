package com.example.chess.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private ConcurrentHashMap<String, SimpMessageHeaderAccessor>map;

    private SimpMessagingTemplate simpMessagingTemplate;

    public RedisMessageSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("@@@@@@@@@@@@@@");

        ObjectMapper objectMapper = new ObjectMapper();

        InternalMessage internalMessage = objectMapper.readValue(message.toString(), InternalMessage.class);

        System.out.println("Message received : " + internalMessage.getToUser());


        if (!Objects.equals(internalMessage.getToUser(), "null")){
            System.out.println("!!!!!!!!!!!!!!!!!!!!"+map.keySet());
            System.out.println("!!!!!!!!!!!!!!!!!!!!"+internalMessage.getToUser());
            if (map.containsKey(internalMessage.getToUser())){
                System.out.println("finallltyyyyyyyyyyyyyyyyyy");

                if (internalMessage.getData().contains("listen on /topic/game/")){
                    System.out.println("finallltyyyyyyyyyyyyyyyyyy2");

                    String input = internalMessage.getData();
                    String regex = "listen on /topic/game/";


                    System.out.println(input);

                    String extracted = input.replaceFirst(regex, "");

                    System.out.println("??????????"+extracted);

                    UUID gameId = UUID.fromString(extracted);






                    map.get(internalMessage.getToUser()).getSessionAttributes().put("game ID",gameId);


                }


            }


        }
        System.out.println("@@@@@@@@@@@@@@");
            simpMessagingTemplate.convertAndSend(internalMessage.getDestination(), internalMessage.getData());
    }

}