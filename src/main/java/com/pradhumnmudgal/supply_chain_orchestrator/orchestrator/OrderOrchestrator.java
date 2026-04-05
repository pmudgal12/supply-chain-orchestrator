package com.pradhumnmudgal.supply_chain_orchestrator.orchestrator;

import com.pradhumnmudgal.supply_chain_orchestrator.model.Order;
import com.pradhumnmudgal.supply_chain_orchestrator.model.OrderStatus;
import com.pradhumnmudgal.supply_chain_orchestrator.repository.OrderRepository;
// import com.pradhumnmudgal.supply_chain_orchestrator.queue.DeadLetterQueue;
import com.pradhumnmudgal.supply_chain_orchestrator.queue.RedisDeadLetterQueue;

// import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderOrchestrator {

    private final OrderRepository orderRepository;
    // private final DeadLetterQueue deadLetterQueue;
    private final RedisDeadLetterQueue deadLetterQueue;
    
    // @Async("taskExecutor") - no longer needed after moving to OrderWorker
    public void processOrder(Long orderId) {

        int maxTries = 3;

        while(true) {
            try {
                Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
            
                System.out.println("Processing Order " + orderId + 
                    " | Attempt: " + order.getRetryCount());

                // simulate failure
                simulateFailure(order);

                // Step 1: PICK
                updateOrderStatus(order, OrderStatus.PICKED);
                simulateDelay();

                // Step 2: PACK
                updateOrderStatus(order, OrderStatus.PACKED);
                simulateDelay();

                // Step 3: SHIP
                updateOrderStatus(order, OrderStatus.SHIPPED);
                simulateDelay();

                // Step 4: DELIVER
                updateOrderStatus(order, OrderStatus.DELIVERED);
                simulateDelay();

                return;
            } catch (Exception e) {
               Order order = orderRepository.findById(orderId).orElseThrow();

               int currentRetryCount = order.getRetryCount() + 1;
               System.out.println("Retry " + currentRetryCount + " for order " + orderId);

               if(currentRetryCount > maxTries) {
                    updateOrderStatus(order, OrderStatus.FAILED);
                    
                    // publish to dead letter queue
                    System.out.println("Publishing to dead letter queue" + order.getId());
                    deadLetterQueue.publish(order.getId());
                    return;
               } else {
                    order.setRetryCount(currentRetryCount);
                    orderRepository.save(order);
               }

               // delay between retries
               simulateDelay();
            }
        }

    }

    private void updateOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        orderRepository.save(order);
        System.out.println("Order " + order.getId() + " -> " + status);
    }

    private void simulateDelay() {
        try {
            Thread.sleep(1000); // Simulate a 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void simulateFailure(Order order) {
        // Simulate a failure by throwing an exception for first 2 tries
        if(order.getRetryCount() < 2) {
            throw new RuntimeException("Order processing failed");
        }
    }
    
}
