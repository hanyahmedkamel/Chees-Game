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
                redisTemplate.opsForHash().put("Available players", player.getName(), objectMapper.writeValueAsString(player));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Long mapSize = redisTemplate.opsForHash().size("Available players");
            if (mapSize != null && mapSize > 1) {
                ObjectMapper objectMapper = new ObjectMapper();
                Player player1=null;
                Player player2 = null;
                try {
                    // Retrieve player from Redis
                    String playerData = (String) redisTemplate.opsForHash().values("Available players").iterator().next();
                     player1 = objectMapper.readValue(playerData, Player.class);

                    // Delete player from Redis
                    redisTemplate.opsForHash().delete("Available players", player1.getName());

                    // Proceed with other logic using player1 if needed

                } catch (Exception e) {
                    // Handle exceptions appropriately
                    e.printStackTrace();
                }
                try {
                    // Retrieve player from Redis
                    String playerData = (String) redisTemplate.opsForHash().values("Available players").iterator().next();
                     player2 = objectMapper.readValue(playerData, Player.class);

                    // Delete player from Redis
                    redisTemplate.opsForHash().delete("Available players", player2.getName());

                    // Proceed with other logic using player1 if needed

                } catch (Exception e) {
                    // Handle exceptions appropriately
                    e.printStackTrace();
                }


                player2.setTeam(false);
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


        redisMessagePublisher.convertAndSend(game.getPlayer1().getName(),"/topic/user/" + game.getPlayer1().getName(), "listen on /topic/game/" + game.getGameId());
        redisMessagePublisher.convertAndSend(game.getPlayer2().getName(),"/topic/user/" + game.getPlayer2().getName(), "listen on /topic/game/" + game.getGameId());
        Thread.sleep(100);
        redisTemplate.opsForValue().set(game.getGameId().toString(), objectMapper.writeValueAsString(game));
        redisMessagePublisher.convertAndSend(game.getPlayer1().getName(),"/topic/game/" + game.getGameId(), game.display());
        redisMessagePublisher.convertAndSend(game.getPlayer2().getName(),"/topic/game/" + game.getGameId(), game.display());
    }
}
