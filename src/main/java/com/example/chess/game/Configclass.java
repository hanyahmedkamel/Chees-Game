package com.example.chess.game;

import com.example.chess.game.ChessEngine.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class Configclass {

    @Bean
    public ConcurrentHashMap<Player, SimpMessageHeaderAccessor> getMap() {
        return new ConcurrentHashMap<>();
    }
    @Bean
    public ConcurrentHashMap<String, WebSocketSession>map(){
         ConcurrentHashMap<String,WebSocketSession>map=new ConcurrentHashMap<>();
         return map;
    };




}
