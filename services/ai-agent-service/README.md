
# AI Agent Service (Production-style)

Features:
- LangChain4j agent with tool calling
- RAG via search-service (HTTP)
- Conversation memory (in-memory; swap to Redis)
- Streaming responses (SSE)
- Kafka intent publishing (optional)

Run:
- Set OPENAI_API_KEY
- Ensure search-service at http://localhost:8085
- Start: ./gradlew bootRun

Endpoints:
POST /ai/chat
GET  /ai/chat/stream
