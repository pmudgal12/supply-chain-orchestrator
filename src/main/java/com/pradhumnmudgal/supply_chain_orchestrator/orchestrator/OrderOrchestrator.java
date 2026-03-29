package com.pradhumnmudgal.supply_chain_orchestrator.orchestrator;

import com.pradhumnmudgal.supply_chain_orchestrator.model.Order;
import com.pradhumnmudgal.supply_chain_orchestrator.model.OrderStatus;
import com.pradhumnmudgal.supply_chain_orchestrator.repository.OrderRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderOrchestrator {

    private final OrderRepository orderRepository;
    
    @Async("taskExecutor")
    public void processOrder(Long orderId) {
        
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
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

        } catch (Exception e) {
            updateOrderStatus(order, OrderStatus.FAILED);
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
    
}
