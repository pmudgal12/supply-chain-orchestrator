package com.pradhumnmudgal.supply_chain_orchestrator.queue;

import com.pradhumnmudgal.supply_chain_orchestrator.orchestrator.OrderOrchestrator;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderWorker {

    // private final OrderQueue orderQueue;
    private final RedisOrderQueue orderQueue;
    private final OrderOrchestrator orderOrchestrator;

    private static final int WORKER_COUNT = 3;

    private volatile boolean running = true; // volatile to ensure visibility across threads

    // Start the worker thread - lifecycle annotation used in Spring Boot to run code right after a bean is created and initialized.
    @PostConstruct 
    public void startWorker() {

        for(int i=1; i<=WORKER_COUNT; i++) {
            int workerId = i;
            new Thread(() -> {
                while (running) {
                    try {
                        System.out.println("Polling queue...");
                        Long orderId = orderQueue.consume();
                        if(orderId == null) {
                            Thread.sleep(1000); // avoid busy loop, added after redis implementation
                            continue;
                        }
                        // System.out.println("Processing from queue: " + orderId);
                        System.out.println("Worker-" + workerId + 
                        " processing order: " + orderId);
                        orderOrchestrator.processOrder(orderId); // retry logic handled by OrderOrchestrator
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "order-worker-thread-" + workerId).start();
        }
    }

    @PreDestroy
    public void stop() {
        running = false;
    }
}
