-- Criação da tabela de Cupons (tb_coupons)
CREATE TABLE tb_coupons (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount_value DECIMAL(10, 2) NOT NULL,
    min_order_value DECIMAL(10, 2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    expires_at TIMESTAMP NOT NULL,
    max_uses INTEGER NOT NULL,
    current_uses INTEGER NOT NULL DEFAULT 0
);

-- Criação da tabela de Avaliações Gerais do Pedido (tb_reviews)
CREATE TABLE tb_reviews (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE,
    rating INTEGER NOT NULL,
    comment VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_review_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

-- Criação da tabela de Avaliações Individuais por Produto (tb_product_reviews)
CREATE TABLE tb_product_reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating INTEGER NOT NULL,
    comment VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_prod_review_user FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE,
    CONSTRAINT fk_prod_review_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- Criação da tabela de Carrossel de Banners (tb_banners)
CREATE TABLE tb_banners (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    display_order INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Criação da tabela do Cartão Fidelidade (tb_fidelities)
CREATE TABLE tb_fidelities (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    order_count INTEGER NOT NULL DEFAULT 0,
    reward_available BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_fidelity_user FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE
);

-- Índices estratégicos para otimizar queries e relatórios do Dashboard
CREATE INDEX idx_coupons_code ON tb_coupons(code);
CREATE INDEX idx_product_reviews_prod ON tb_product_reviews(product_id);
CREATE INDEX idx_banners_order ON tb_banners(display_order) WHERE active = TRUE;
