package com.pradhumnmudgal.supply_chain_orchestrator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String pincode;
    
    private int capacity;
}
