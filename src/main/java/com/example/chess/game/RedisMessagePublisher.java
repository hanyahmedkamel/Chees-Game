package com.example.chess.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher  {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private  SimpMessagingTemplate simpMessagingTemplate;

    public RedisMessagePublisher(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void publish(InternalMessage message) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // Serialize to JSON
        String json = mapper.writeValueAsString(message);



        redisTemplate.convertAndSend("stackfortech", json);

        simpMessagingTemplate.convertAndSend(message.getDestination(), message.getData());

    }
    public void convertAndSend(String channel,String data) throws JsonProcessingException {
        InternalMessage internalMessage=new InternalMessage(channel,data);
        publish(internalMessage);
    }

    public void convertAndSend(String touser,String channel,String data) throws JsonProcessingException {
        InternalMessage internalMessage=new InternalMessage(channel,data);
        internalMessage.setToUser(touser);
        publish(internalMessage);
    }
}