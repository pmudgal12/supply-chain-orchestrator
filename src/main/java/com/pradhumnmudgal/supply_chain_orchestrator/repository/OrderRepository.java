package com.pradhumnmudgal.supply_chain_orchestrator.repository;

import com.pradhumnmudgal.supply_chain_orchestrator.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
