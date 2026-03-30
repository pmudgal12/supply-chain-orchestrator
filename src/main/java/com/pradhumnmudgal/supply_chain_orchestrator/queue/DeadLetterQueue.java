package com.pradhumnmudgal.supply_chain_orchestrator.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

@Component
public class DeadLetterQueue {

    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    public void publish(Long orderId) {
        queue.offer(orderId); // non-blocking
    }

    public Long consume() throws InterruptedException {
        return queue.take(); // blocks until an element is available
    }
    
}
