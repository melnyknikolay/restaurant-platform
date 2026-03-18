
CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS menu_embeddings (
    id UUID PRIMARY KEY,
    content TEXT,
    embedding vector(1536)
);
