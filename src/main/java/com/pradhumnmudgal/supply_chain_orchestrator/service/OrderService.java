package com.pradhumnmudgal.supply_chain_orchestrator.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.pradhumnmudgal.supply_chain_orchestrator.model.Order;
import com.pradhumnmudgal.supply_chain_orchestrator.model.OrderStatus;
import com.pradhumnmudgal.supply_chain_orchestrator.model.Warehouse;
import com.pradhumnmudgal.supply_chain_orchestrator.repository.OrderRepository;
import com.pradhumnmudgal.supply_chain_orchestrator.repository.WarehouseRepository;
import com.pradhumnmudgal.supply_chain_orchestrator.orchestrator.OrderOrchestrator;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;
    private final OrderOrchestrator orderOrchestrator;

    public Order createOrder(Order order) {

        // Set the order status to CREATED - initial status
        order.setStatus(OrderStatus.CREATED);

        // Assign the order to the suitable warehouse
        Warehouse selectedWarehouse = selectWarehouse(order);
        
        if(selectedWarehouse != null) {
            order.setWarehouseId(selectedWarehouse.getId());
        } else {
            order.setStatus(OrderStatus.FAILED);
        }

        Order savedOrder = orderRepository.save(order);

        // Start the order processing
        orderOrchestrator.processOrder(savedOrder.getId());

        // Save the order
        return savedOrder;
    }

    private Warehouse selectWarehouse(Order order) {
        // Implement logic to find the suitable warehouse based on order details

        List<Warehouse> warehouses = warehouseRepository.findAll();

        for(Warehouse warehouse : warehouses) {
            if(warehouse.getCapacity() >= order.getQuantity()) {
                return warehouse;
            }
        }
        return null;
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
}
