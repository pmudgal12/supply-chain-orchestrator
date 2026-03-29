package com.pradhumnmudgal.supply_chain_orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SupplyChainOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplyChainOrchestratorApplication.class, args);
	}

}
