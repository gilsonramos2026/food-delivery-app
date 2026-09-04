-- Criação da tabela de Usuários (tb_users)
CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(180) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(20) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- Criação da tabela de Códigos de Verificação (tb_verification_codes)
CREATE TABLE tb_verification_codes (
    id BIGSERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    code VARCHAR(6) NOT NULL,
    expires_at TIMESTAMP NOT NULL
);

-- Index opcional para otimizar a busca do último código por telefone usada no Repository
CREATE INDEX idx_verification_phone_expires ON tb_verification_codes(phone, expires_at DESC);
