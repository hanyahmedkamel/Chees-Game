package com.example.chess.game;

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

    public void publish(InternalMessage message) {



        redisTemplate.convertAndSend(message.getDestination(), message.getData());

        simpMessagingTemplate.convertAndSend(message.getDestination(), message.getData());

    }
    public void convertAndSend(String channel,String data){
        InternalMessage internalMessage=new InternalMessage(channel,data);
        publish(internalMessage);
    }
}