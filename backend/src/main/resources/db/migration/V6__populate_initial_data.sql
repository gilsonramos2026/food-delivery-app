-- 1. Inserção de Usuários Base (Senha criptografada em BCrypt para teste: 'senha123')
-- Admin, Cliente de testes e Entregador
INSERT INTO tb_users (name, email, password, phone, role, created_at)
VALUES
('Admin Supremo', 'admin@delivery.com', '$2a$10$wKbcK03pB5iEEx66BwAneu1Y9085kAg086N8VzW5C5rre.W7gYJ8q', '11999999999', 'ADMIN', NOW()),
('Carlos Cliente', 'carlos@email.com', '$2a$10$wKbcK03pB5iEEx66BwAneu1Y9085kAg086N8VzW5C5rre.W7gYJ8q', '11988888888', 'CLIENT', NOW()),
('Fabio Entregador', 'fabio@motoboy.com', '$2a$10$wKbcK03pB5iEEx66BwAneu1Y9085kAg086N8VzW5C5rre.W7gYJ8q', '11977777777', 'COURIER', NOW())
ON CONFLICT (phone) DO NOTHING;

-- 2. Inserção de Categorias do Cardápio
INSERT INTO categories (name, display_order)
VALUES
('Pizzas Tradicionais', 1),
('Pizzas Doces', 2),
('Bebidas', 3),
('Sobremesas', 4);

-- 3. Inserção de Produtos com Preço de Venda e Preço de Custo (Para validação do Dashboard)
INSERT INTO products (name, description, price, cost_price, image_url, category_id)
VALUES
('Pizza Calabresa', 'Molho de tomate, mozarela, calabresa fatiada e cebola.', 45.90, 18.50, '/uploads/seed-calabresa.jpg', 1),
('Pizza Quatro Queijos', 'Molho de tomate, mozarela, provolone, gorgonzola e catupiry.', 49.90, 22.00, '/uploads/seed-quatro-queijos.jpg', 1),
('Pizza Prestígio', 'Chocolate ao leite, coco ralado e cerejas.', 39.90, 15.00, '/uploads/seed-prestigio.jpg', 2),
('Coca-Cola 2L', 'Refrigerante garrafa PET de 2 litros bem gelada.', 11.90, 6.20, '/uploads/seed-coca.jpg', 3),
('Guaraná Antarctica 2L', 'Refrigerante garrafa PET de 2 litros.', 10.90, 5.80, '/uploads/seed-guarana.jpg', 3),
('Pudim de Leite Moça', 'Fatia de pudim artesanal com calda de caramelo.', 12.00, 4.00, '/uploads/seed-pudim.jpg', 4);

-- 4. Inserção de Cupons de Desconto Ativos e Válidos (isValidoPara)
INSERT INTO tb_coupons (code, discount_value, min_order_value, active, expires_at, max_uses, current_uses)
VALUES
('QUERO10', 10.00, 40.00, TRUE, '2030-12-31 23:59:59', 500, 12),
('PROMO5', 5.00, 20.00, TRUE, '2030-12-31 23:59:59', 1000, 45),
('VIP25', 25.00, 100.00, TRUE, '2030-12-31 23:59:59', 50, 0);

-- 5. Inserção de Banners Ativos Ordenados para a Home
INSERT INTO tb_banners (title, image_url, display_order, active)
VALUES
('Combo Família: Pizza + Refri 2L com Desconto', '/uploads/banner-combo.jpg', 1, TRUE),
('Sextou! Compre qualquer Pizza Doce com 15% OFF', '/uploads/banner-sextou.jpg', 2, TRUE),
('Taxa de Entrega Grátis num raio de até 3km', '/uploads/banner-taxa-gratis.jpg', 3, TRUE);

-- 6. Inicialização do Cartão Fidelidade para os Usuários cadastrados
INSERT INTO tb_fidelities (user_id, order_count, reward_available)
VALUES
(2, 4, FALSE), -- Carlos Cliente já possui 4 pedidos concluídos no ciclo atual
(3, 0, FALSE)
ON CONFLICT (user_id) DO NOTHING;
