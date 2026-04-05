package com.pradhumnmudgal.supply_chain_orchestrator.queue;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisOrderQueue {

    private static final String QUEUE_NAME = "order_queue";

    private final StringRedisTemplate redisTemplate;

    public RedisOrderQueue(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(Long orderId) {
        redisTemplate.opsForList().leftPush(QUEUE_NAME, orderId.toString());
    }

    public Long consume() {
        String value = redisTemplate.opsForList().rightPop(QUEUE_NAME, 5, TimeUnit.SECONDS); // non blocking pop
        if (value != null) {
            return Long.parseLong(value);
        }
        return null;
    }
    
}
