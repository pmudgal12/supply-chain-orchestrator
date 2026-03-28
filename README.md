# Smart Supply Chain Orchestrator (Kubernetes-backed)

## 🚀 Overview

The **Smart Supply Chain Orchestrator** is a backend system designed to simulate and optimize real-world order fulfillment workflows. It models how modern logistics systems handle orders, make intelligent decisions, and execute multi-step processes reliably at scale.

This project focuses on **backend engineering, distributed systems concepts, and Kubernetes-based execution**, making it a strong showcase for system design and production-ready thinking.

---

## 🎯 Objectives

- Build a **real-world inspired backend system**
- Demonstrate **system design fundamentals**
- Gain hands-on experience with:

  - Java + Spring Boot
  - Database design (PostgreSQL)
  - Workflow orchestration
  - Asynchronous processing
  - Kubernetes (later phases)

---

## 🧠 Core Concept

An order goes through a **workflow pipeline**:

```
CREATE → PICK → PACK → SHIP → DELIVER
```

The system:

1. Accepts orders
2. Selects the best warehouse
3. Orchestrates execution steps
4. Handles failures and retries (future phases)
5. Scales execution using distributed workers (Kubernetes)

---

## 🏗️ Current Features (Phase 1)

- REST API to create and fetch orders
- Warehouse data model
- Basic warehouse selection logic
- Order lifecycle tracking using status
- PostgreSQL integration via JPA

---

## 📦 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker** (for DB)
- **Lombok**

---

## 📁 Project Structure

```
src/main/java/com/yourname/orchestrator/
 ├── controller/     # REST APIs
 ├── service/        # Business logic
 ├── repository/     # Data access layer
 ├── model/          # Entities and enums
 ├── orchestrator/   # Workflow engine (future)
 └── config/         # Configurations
```

---

## 🧾 Data Models

### Order

- id
- item
- quantity
- destinationPincode
- status
- warehouseId

### Warehouse

- id
- name
- pincode
- capacity

### OrderStatus

- CREATED
- PICKED
- PACKED
- SHIPPED
- DELIVERED
- FAILED

---

## ⚙️ Setup Instructions

### 1. Clone the repository

```bash
git clone <repo-url>
cd supply-chain-orchestrator
```

---

### 2. Start PostgreSQL using Docker

```bash
docker run --name postgres-orchestrator \
  -e POSTGRES_DB=orchestrator_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres
```

---

### 3. Configure application.properties

```
spring.datasource.url=jdbc:postgresql://localhost:5432/orchestrator_db
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 4. Run the application

```bash
./mvnw spring-boot:run
```

---

### 5. Test endpoint

```
GET /health
```

---

## 🔜 Roadmap

### Phase 2

- Improved warehouse selection (distance, cost)
- Service layer abstraction

### Phase 3

- Workflow orchestration engine
- Retry and failure handling

### Phase 4

- Asynchronous processing (queue-based)

### Phase 5

- Dockerize services
- Deploy on Kubernetes
- Use Jobs for task execution

### Phase 6 (Advanced)

- Priority-based scheduling
- Dynamic routing
- Observability (logs + metrics)

---

## 🧠 Key Learnings

This project demonstrates:

- Designing scalable backend systems
- Handling state transitions and workflows
- Structuring maintainable Spring Boot applications
- Transitioning from monolith → distributed system

---

## 🤝 Future Enhancements

- Event-driven architecture (Kafka)
- Distributed locking
- Rate limiting
- Real-time tracking
- UI dashboard

---

## 📌 Notes

- This project is intentionally built **incrementally**
- Focus is on **depth over features**
- Designed to evolve into a **production-grade system**

---

## 👨‍💻 Author

---
