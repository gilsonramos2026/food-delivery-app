-- Criação da tabela de Pedidos (orders)
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    address VARCHAR(500) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    delivery_fee DECIMAL(10, 2) NOT NULL,
    scheduled_at TIMESTAMP,
    cancellation_reason VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_client FOREIGN KEY (client_id) REFERENCES tb_users (id) ON DELETE RESTRICT
);

-- Criação da tabela de Itens do Pedido (order_items)
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE RESTRICT
);

-- Índices para otimizar relatórios e buscas de pedidos
CREATE INDEX idx_orders_client_id ON orders(client_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
