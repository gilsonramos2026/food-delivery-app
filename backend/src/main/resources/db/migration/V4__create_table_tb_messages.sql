-- Criação da tabela para o histórico do chat em tempo real
CREATE TABLE tb_messages (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    sender_phone VARCHAR(20) NOT NULL,
    sender_name VARCHAR(100) NOT NULL,
    sender_role VARCHAR(30) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    CONSTRAINT fk_message_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

-- Índice para acelerar o carregamento cronológico do histórico do chat
CREATE INDEX idx_tb_messages_order_id_time ON tb_messages(order_id, timestamp ASC);
