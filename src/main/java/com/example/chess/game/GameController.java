package com.example.chess.game;

import com.example.chess.game.ChessEngine.Game;
import com.example.chess.game.ChessEngine.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {

    private final RedisMessagePublisher redisMessagePublisher;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SessionRepository sessionRepository;
    private final ConcurrentHashMap<String, SimpMessageHeaderAccessor> concurrentHashMap;
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ConcurrentHashMap<String, WebSocketSession>map;
    @Autowired
    public GameController(RedisMessagePublisher redisMessagePublisher, ApplicationEventPublisher applicationEventPublisher, SessionRepository sessionRepository, ConcurrentHashMap<String, SimpMessageHeaderAccessor> concurrentHashMap, RedisTemplate<String, String> redisTemplate) {
        this.redisMessagePublisher = redisMessagePublisher;
        this.applicationEventPublisher = applicationEventPublisher;
        this.sessionRepository = sessionRepository;
        this.concurrentHashMap = concurrentHashMap;
        this.redisTemplate = redisTemplate;
    }
    @MessageMapping("/start")
    public void start(@Payload String message, SimpMessageHeaderAccessor accessor) throws JsonProcessingException {
        redisMessagePublisher.convertAndSend("/topic/user/" + message, "hello");
        Player player = new Player(message, true);
        accessor.getSessionAttributes().put("player name",message);
        concurrentHashMap.put(player.getName(), accessor);
        applicationEventPublisher.publishEvent(player);
    }

    @MessageMapping("/makeMove")
    public void makeMove(@Payload Message message, SimpMessageHeaderAccessor accessor) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UUID gameId = (UUID) accessor.getSessionAttributes().get("game ID");
        String g = redisTemplate.opsForValue().get(gameId.toString());
        Game game = objectMapper.readValue(g, Game.class);


        redisMessagePublisher.convertAndSend("/topic/game/" + game.getGameId(), objectMapper.writeValueAsString(game.startGame((String) accessor.getSessionAttributes().get("player name"),message.getPieceName(),message.getX(),message.getY())));

        redisTemplate.opsForValue().set(game.getGameId().toString(), objectMapper.writeValueAsString(game));

        if (game.winner!=null){
            redisMessagePublisher.convertAndSend("/topic/game/" + game.getGameId(), objectMapper.writeValueAsString(game.winner.getName()));
            map.get(accessor.getSessionId()).close();
        }
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent sessionDisconnectEvent) throws JsonProcessingException {
        StompHeaderAccessor accessor = (StompHeaderAccessor) SimpMessageHeaderAccessor.getAccessor(sessionDisconnectEvent.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();


        if (accessor.getSessionAttributes().get("game ID") != null) {
            UUID gameId = (UUID) accessor.getSessionAttributes().get("game ID");
            String g = redisTemplate.opsForValue().get(gameId.toString());
            Game game = objectMapper.readValue(g, Game.class);

            accessor.getSessionAttributes().remove("game ID");
            redisTemplate.delete(game.getGameId().toString());
        }

        String playerName = (String) accessor.getSessionAttributes().get("player name");
        String hashKey = "Available players";

        if (redisTemplate.opsForHash().hasKey(hashKey, playerName)) {
            redisTemplate.opsForHash().delete(hashKey, playerName);
        }
    }



}
