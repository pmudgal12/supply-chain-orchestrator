package com.pradhumnmudgal.supply_chain_orchestrator.queue;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisDeadLetterQueue {

    private static final String DLQ_NAME = "dead_letter_queue";

    private final StringRedisTemplate redisTemplate;

    public void publish(Long orderId) {
        redisTemplate.opsForList().leftPush(DLQ_NAME, String.valueOf(orderId));
    }

    public Long consume() {
        String value = redisTemplate.opsForList().rightPop(DLQ_NAME, 5, TimeUnit.SECONDS); // non blocking pop
        if (value != null) {
            return Long.parseLong(value);
        }
        return null;
    }
    
}
