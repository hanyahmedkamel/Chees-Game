package com.example.chess.game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

//@Service
public class RedisListService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void addToListAtomic(String value) {

        RedisCallback<Object> redisCallback = connection -> {
            connection.multi();
            redisTemplate.opsForList().rightPush("Available players", value);
            Long listSize = redisTemplate.opsForList().size("Available players");
            System.out.println(listSize);
            if (listSize != null && listSize > 1) {
                System.out.println("List size is greater than 1. Executing additional operations...");



            }
            connection.exec();
            return null;
        };

        redisTemplate.execute(redisCallback);
    }



}
