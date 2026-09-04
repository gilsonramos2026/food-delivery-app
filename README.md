# 🍕 Delivery App API

Uma API REST e WebSocket robusta, segura e escalável para gerenciamento de um ecossistema completo de Delivery. O sistema conta com autenticação passwordless via celular, controle estrito de acessos, máquina de estados para pedidos, chat e rastreamento em tempo real, integração de pagamentos e painel gerencial [^1].

## 🚀 Funcionalidades do Ecossistema

### 🔒 1. Autenticação & Gestão de Usuários
* **Login Passwordless (Sem senha):** Envio de código dinâmico OTP gerado no console e validação via API [^1].
* **Autocreate integrado:** Usuários novos que entram pelo telefone nascem automaticamente na hora com o papel `CLIENT` [^1].
* **Controle por Roles:** Bloqueio e liberação de rotas administrativas (`ADMIN`) e de logística (`COURIER`) blindadas pelo Spring Security com prefixo `ROLE_` [^1].

### 🍕 2. Cardápio Inteligente com Controle de Custo
* **Categorias:** CRUD completo estruturado com gerenciamento fino de ordem de exibição (`display_order`).
* **Produtos:** Cadastro de itens contendo preço de venda e o preço de custo de fabricação (essencial para auditoria de lucro real).
* **Upload de Mídia:** Upload físico de fotos gerando nomes únicos via **UUID**, salvos localmente e mapeados para URLs estáticas públicas.
* **Menu Filtrado:** Listagem pública de produtos segmentada dinamicamente por ID de categoria.

### 📦 3. Pedidos, Máquina de Estados & Logística
* **Histórico Imutável:** Tabela de pedidos e itens fixando a quantidade e o preço exato do produto no momento histórico da compra.
* **Máquina de Estados (State Machine):** Fluxo lógico centralizado por mapas de transição permitidos que impede pulos de etapa ou reabertura de pedidos finalizados.
* **Rastreamento Dinâmico (GPS):** Envio de coordenadas de latitude/longitude (`LocalizacaoDTO`) ao vivo pelo motoboy e propagadas aos clientes.
* **Chat Persistente em Tempo Real:** Comunicação via WebSockets integrada à tabela do banco de dados (`tb_messages`), permitindo histórico persistente de conversas entre cliente, entregador e admin no mesmo pedido.

### 💳 4. Integração de Pagamento Avançado
* **Stripe Integrado:** Geração automática de `clientSecret` para inicialização do Stripe Elements no frontend para pedidos `RECEBIDO`.
* **Webhook de Confirmação:** Endpoint público e seguro preparado para receber notificações do Stripe, alterando o status do pedido para `PREPARANDO` automaticamente após o sucesso da transição.

### 🎟️ 5. Marketing, Fidelidade & Avaliações
* **Motor de Cupons:** Validação lógica (`validateAndGetCoupon`) que barra códigos desativados, expirados, com limite de uso estourado ou pedidos abaixo do valor mínimo.
* **Cartão Fidelidade Automático:** Contador persistente que adiciona +1 ponto a cada entrega concluída. Ao atingir 10 pedidos, destrava um brinde para o usuário e reinicia o ciclo.
* **Banners Dinâmicos:** Filtro e exibição de banners promocionais ativos ordenados para a Home.
* **Avaliações Isoladas:** Notas e comentários separados de forma que a nota do atendimento geral do pedido não interfira nas notas individuais dos produtos.

### 📊 6. Dashboard Administrativo
* **Rentabilidade Real:** Cálculo nativo direto no banco de dados agregando o faturamento total acumulado e o lucro líquido real calculando `Preço de Venda - Preço de Custo` dos produtos vendidos.
* **Ranking de Vendas:** Ranking dos Top 5 produtos mais comercializados do mês na plataforma.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21** e **Spring Boot 4.x** (Core do projeto)
* **Spring Security** & **JWT (Json Web Token)** (Segurança e controle de privilégios)
* **Spring WebSocket (STOMP / SockJS)** (Mensageria e chat em tempo real)
* **Spring Data JPA** & **Hibernate 7.x** (Persistência de dados)
* **PostgreSQL** (Banco de dados de produção local)
* **Flyway Migration** (Versionamento evolutivo do banco de dados)
* **Lombok** (Produtividade e eliminação de boilerplate)
* **Stripe Java SDK** (Gateway de pagamento)
* **Springdoc OpenAPI v3 (Swagger UI)** (Documentação interativa da API)

---

## 📁 Estrutura de Pastas (Plana e Singular)

```text
backend/src/main/java/com/delivery/
├── config/              # Configurações globais (Swagger, WebMvc, WebSockets)
├── controller/          # Controladores HTTP REST e WebSockets Stomp
├── dto/
│   ├── request/         # Objetos de entrada com Bean Validation e Swagger
│   └── response/        # Objetos de saída e payloads de erro limpos
├── exception/           # Interceptador global de erros e exceções
├── mapper/              # Conversores de tipo (Entidade <-> DTO) usando Component
├── model/               # Entidades de Banco de Dados e Enums
├── repository/          # Interfaces de Acesso ao Banco (Spring Data JPA)
└── service/             # Interfaces de negócio e subpasta service/impl/
```

---

## 🚀 Como Executar o Projeto Localmente

### 1. Clonar o Repositório e Configurar os Perfis
Certifique-se de ter um banco vazio no seu PostgreSQL local chamado `delivery_db`. No arquivo `backend/src/main/resources/application.yml`, configure o perfil ativo para `prod` e preencha as credenciais do seu banco local.

### 2. Executar a Aplicação
Execute o projeto através da sua IDE (IntelliJ IDEA) utilizando a classe principal `BackendApplication.java`. O **Flyway** rodará de forma sequencial na inicialização criando todas as tabelas, índices e aplicando a **carga de Seed Completa (`V6`)** [^1].

### 3. Acessar a Documentação Interativa
Com o servidor ligado em modo de produção local, acesse a interface visual do Swagger pelo navegador:
👉 **[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)**

### 👤 Dados de Massa para Testes Rápidos (Seed)
Graças à migration `V6`, você já pode testar o sistema usando estas credenciais de telefone (todas as senhas base são `senha123`):
* **Telefone do ADMIN:** `11999999999` [^1]
* **Telefone do CLIENT:** `11988888888` [^1]
* **Telefone do COURIER:** `11977777777` [^1]
