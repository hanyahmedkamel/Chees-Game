package com.example.chess.game;

import com.example.chess.game.ChessEngine.Game;
import com.example.chess.game.ChessEngine.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Events {
    private final RedisMessagePublisher redisMessagePublisher;
    private final BlockingQueue<Player> blockingQueue = new LinkedBlockingQueue<>();
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ConcurrentHashMap<Player, SimpMessageHeaderAccessor> concurrentHashMap;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public Events(RedisMessagePublisher redisMessagePublisher, ApplicationEventPublisher applicationEventPublisher, RedisTemplate<String, String> redisTemplate) {
        this.redisMessagePublisher = redisMessagePublisher;
        this.applicationEventPublisher = applicationEventPublisher;
        this.redisTemplate = redisTemplate;
    }


    @Async
    @EventListener
    public void addPlayer(Player player)  {

        RedisCallback<Object> redisCallback = connection -> {
            connection.multi();
            try {
                System.out.println(objectMapper.writeValueAsString(player).toString());
                redisTemplate.opsForList().rightPush("Available players", objectMapper.writeValueAsString(player));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Long listSize = redisTemplate.opsForList().size("Available players");
            System.out.println(listSize);
            if (listSize != null && listSize > 1) {
                ObjectMapper objectMapper=new ObjectMapper();
                Player player1;
                Player player2;
                try {
                     player1= objectMapper.readValue(redisTemplate.opsForList().leftPop("Available players"),Player.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                try {
                    player2= objectMapper.readValue(redisTemplate.opsForList().leftPop("Available players"),Player.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                player2.setTeam(false);
                System.out.println(player1.getName()+"  "+player2.getName());
                applicationEventPublisher.publishEvent(new Game(player1, player2));


            }
            connection.exec();
            return null; // Return type specified as Object or Void
        };

        redisTemplate.execute(redisCallback);
    }


    @Async
    @EventListener
    public void startGame(Game game) throws InterruptedException, JsonProcessingException {
        System.out.println(game.getGameId());
        concurrentHashMap.get(game.getPlayer1()).getSessionAttributes().put("game ID", game.getGameId());
        concurrentHashMap.get(game.getPlayer2()).getSessionAttributes().put("game ID", game.getGameId());

        redisMessagePublisher.convertAndSend("/topic/user/" + game.getPlayer1().getName(), "listen on /topic/game/" + game.getGameId());
        redisMessagePublisher.convertAndSend("/topic/user/" + game.getPlayer2().getName(), "listen on /topic/game/" + game.getGameId());
        Thread.sleep(100);
        redisMessagePublisher.convertAndSend("/topic/game/" + game.getGameId(), game.display());
        redisTemplate.opsForValue().set(game.getGameId().toString(), objectMapper.writeValueAsString(game));
    }
}
