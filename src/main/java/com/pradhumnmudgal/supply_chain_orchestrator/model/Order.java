package com.pradhumnmudgal.supply_chain_orchestrator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;

    private int quantity;

    private String destinationPincode;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long warehouseId; // selected warehouse
}


