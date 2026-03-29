package com.pradhumnmudgal.supply_chain_orchestrator.repository;

import com.pradhumnmudgal.supply_chain_orchestrator.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    
}
