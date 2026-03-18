# 🍽️ Restaurant Platform 

High-load event-driven microservices platform built with Spring Boot, Kafka and AI components.

The system processes asynchronous workflows (orders → payments → notifications), supports hybrid search (Elasticsearch + vector embeddings), and integrates external APIs (PayPal, LLM providers).


## 🚧 Project Status

This project is actively under development.

Current focus:
- Hybrid search implementation
- AI agent orchestration
- Observability stack


## ⚙️ Tech Stack

### Backend
- Java 21, Spring Boot 3
- Spring Web / WebFlux
- Spring Cloud (Gateway, Config, Discovery)
- Resilience4j (Retry, Circuit Breaker)

### Data
- PostgreSQL
- Redis
- Elasticsearch (search)

### Messaging & Streaming
- Apache Kafka
- Kafka Connect + Debezium (Outbox pattern)
- Avro + Schema Registry

### Security
- Keycloak (OAuth2 / OpenID Connect)
- JWT

### Observability
- Prometheus, Grafana
- Loki (logs)
- Tempo / Zipkin (tracing)

### DevOps
- Docker / Docker Compose
- Kubernetes (planned)
- GitHub Actions (CI/CD)

### Frontend
- React / Next.js (SSE, real-time notifications)

## 🧩 Architecture Overview

The system is designed using microservices architecture with event-driven communication.

Main principles:
- Decoupled services
- Asynchronous communication via Kafka
- API Gateway as a single entry point
- Distributed tracing and observability
- External integrations (PayPal, AI services)

### Core Services

- **Menu Service** – manages menu data
- **Order Service** – handles order lifecycle
- **Payment Service** – integrates with PayPal and processes payments
- **Notification Service** – real-time notifications via SSE/WebSocket
- **Search Service** – full-text search (Elasticsearch)
- **AI Agent Service** – personalized suggestions
- **Gateway Service** – API gateway

## 🔄 Event-Driven Flow

```text
Client → Gateway → Order Service
                     ↓
              orders.created
                     ↓
                Kafka
                     ↓
              Payment Service
                     ↓
           payments.completed
                     ↓
          Notification Service
                     ↓
               SSE → Client
```

## 🚀 Features

- Microservices architecture
- Event-driven communication (Kafka)
- Payment integration (PayPal)
- Real-time notifications (SSE)
- Distributed tracing & monitoring
- External API integrations
- Reactive services (WebFlux)
- Scalable and cloud-ready design

## 📊 Observability

- Metrics: Micrometer + Prometheus
- Dashboards: Grafana (request latency, Kafka lag, error rate)
- Logs: Loki
- Tracing: Zipkin (distributed tracing across services)

## ▶️ Run locally

```bash
docker-compose up --build

Services will be available via API Gateway:
http://localhost:8080

This will start:

- PostgreSQL
- Kafka + Zookeeper
- Elasticsearch + Kibana
- Redis
- All microservices
```

## ☁️ Cloud & Scalability

The platform is designed to be deployed on Kubernetes and cloud environments (AWS/GCP).

Planned:
- Kubernetes deployment
- Helm charts
- Terraform infrastructure
- AWS integrations (S3, SQS, RDS)

### AI Agent Service

The AI Agent Service acts as the orchestration and reasoning layer of the platform.

Unlike a traditional chatbot, the agent is capable of:

- Calling internal services (search, order, recommendation)
- Executing workflows via Kafka
- Generating structured intents
- Using Retrieval-Augmented Generation (RAG)
- Maintaining conversation context

### Responsibilities

- Interpret user intent using LLM (LangChain4j)
- Select and execute tools dynamically
- Orchestrate multi-step workflows
- Integrate with search-service for context retrieval
- Publish domain events to Kafka

---

### Interaction with Search Service

The AI Agent does not replace the search service.

Instead, it leverages it as a retrieval layer:

User Query → AI Agent → search-service → context → LLM → response

This ensures:
- low latency
- high relevance
- cost efficiency

---

### Why separate Search and AI?

We follow a hybrid architecture:

- search-service → fast, deterministic retrieval (Elasticsearch + vector search)
- ai-agent-service → reasoning, orchestration, and generation

This separation improves scalability, performance, and maintainability.

## 🧠 Purpose

This project is designed as a production-like system to demonstrate skills in:

- Distributed systems design
- Event-driven architecture
- Backend engineering at scale
- System integration and API design

## 💡 Highlights

- Hybrid search (vector + Elasticsearch ranking fusion)
- AI agent with tool-calling (LangChain4j)
- Event-driven architecture with Kafka
- Resilience patterns (retry, circuit breaker)
- Distributed tracing across services

