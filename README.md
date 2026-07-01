# SinalizaAI — Back-end do Site

Backend oficial da plataforma **SinalizaAI** ([sinalizaai.com](https://www.sinalizaai.com/)), responsável por sustentar as operações do site institucional/comercial da empresa: cadastro e autenticação de clientes (empresas), catálogo de produtos, formação de pedidos, processamento de pagamentos, emissão e controle de licenças de uso do software, e recebimento de mensagens de contato.

A API é construída em **Java 17 + Spring Boot 3**, segue o padrão REST, é protegida por **autenticação JWT** e documentada via **Swagger/OpenAPI**. O banco de dados é **MySQL**, versionado com **Flyway**, e a aplicação é conteinerizada com **Docker**, com deploy contínuo na **Railway**.

> Repositório: [`SinalizaAI/Back_end_Site`](https://github.com/SinalizaAI/Back_end_Site)
> Módulo principal: [`sinalizaai-back`](./sinalizaai-back)

---

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Arquitetura e stack tecnológica](#arquitetura-e-stack-tecnológica)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Modelo de domínio](#modelo-de-domínio)
- [Autenticação e segurança](#autenticação-e-segurança)
- [Documentação interativa (Swagger)](#documentação-interativa-swagger)
- [Referência das APIs](#referência-das-apis)
  - [Auth](#auth--apiauth)
  - [Clientes](#clientes--apiclientes)
  - [Produtos](#produtos--apiprodutos)
  - [Pedidos](#pedidos--apipedidos)
  - [Itens do Pedido](#itens-do-pedido--apiitens-pedido)
  - [Pagamentos](#pagamentos--apipagamentos)
  - [Licenças](#licenças--apilicencas)
  - [Contatos](#contatos--apicontatos)
- [Tratamento de erros](#tratamento-de-erros)
- [Variáveis de ambiente](#variáveis-de-ambiente)
- [Executando localmente](#executando-localmente)
- [Executando com Docker](#executando-com-docker)
- [Migrações de banco de dados](#migrações-de-banco-de-dados)
- [Deploy](#deploy)
- [Testes](#testes)
- [Roadmap / observações técnicas](#roadmap--observações-técnicas)
- [Contribuindo](#contribuindo)

---

## Sobre o projeto

O **SinalizaAI** é o produto de software cujo site oficial (https://www.sinalizaai.com) é sustentado por esta API. Este backend não é apenas um site institucional estático: ele implementa um fluxo comercial completo de **venda e licenciamento** do produto, permitindo que empresas (clientes) se cadastrem, façam login, montem pedidos a partir do catálogo de produtos, efetuem pagamentos e recebam suas **licenças de uso** — chaves únicas com data de início e expiração, que podem ser ativadas ou desativadas conforme a validade do contrato.

Em resumo, a API cobre quatro grandes frentes de negócio:

1. **Gestão de clientes (empresas)** — cadastro, autenticação, atualização de dados e desativação de contas B2B (identificadas por CNPJ).
2. **Catálogo e vendas** — produtos, pedidos e itens de pedido, com cálculo e controle de status (`pendente`, `pago`, `cancelado`).
3. **Pagamentos e licenciamento** — registro/aprovação/recusa/estorno de pagamentos e geração de licenças de software vinculadas a um cliente e a um pedido.
4. **Relacionamento** — canal de contato do site (fale conosco), com controle de mensagens lidas/não lidas.

---

## Arquitetura e stack tecnológica

| Camada | Tecnologia |
|---|---|
| Linguagem / Runtime | Java 17 |
| Framework | Spring Boot 3.5.14 |
| Persistência | Spring Data JPA (Hibernate) |
| Banco de dados | MySQL (via `mysql-connector-j`) |
| Migrações de schema | Flyway (`flyway-core`, `flyway-mysql`) |
| Segurança | Spring Security + JWT (`java-jwt`, Auth0) + BCrypt |
| Validação | Bean Validation (`spring-boot-starter-validation`) |
| Documentação de API | springdoc-openapi (Swagger UI) |
| Boilerplate | Lombok |
| Build | Maven (`mvnw`) |
| Empacotamento | Docker (multi-stage build) |
| Hospedagem | Railway |

O projeto segue uma organização em camadas típica de aplicações Spring Boot:

```
Controller  →  Service  →  Repository  →  Entity (Domain)
     ↑             ↑
   DTOs      Regras de negócio
```

- **`controller/`** — endpoints REST, validação de entrada e códigos HTTP.
- **`service/`** — regras de negócio, orquestração entre repositórios.
- **`domain/`** — entidades JPA (agregadas por pasta: `cliente`, `pedido`, `produto`, `itempedido`, `pagamento`, `licenca`, `contato`) e seus respectivos `Repository`.
- **`dto/`** — objetos de entrada (`Cadastro*DTO`, `Atualizacao*DTO`, `LoginDTO`) e saída (`*ResponseDTO`), além de DTOs de erro (`ErroResponseDTO`, `ErroValidacaoDTO`).
- **`security/`** — `SecurityConfig` (regras de autorização) e `SecurityFilter` (filtro JWT stateless).
- **`config/`** — `CorsConfig` (origens permitidas) e `SwaggerConfig` (metadados do OpenAPI).
- **`infra/`** — `GlobalExceptionHandler`, tratamento centralizado de exceções.

---

## Estrutura de pastas

```
Back_end_Site/
└── sinalizaai-back/
    ├── Dockerfile
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/sinalizaai/sinalizaai_back/
        │   │   ├── SinalizaaiBackApplication.java
        │   │   ├── config/
        │   │   │   ├── CorsConfig.java
        │   │   │   └── SwaggerConfig.java
        │   │   ├── controller/
        │   │   │   ├── AuthController.java
        │   │   │   ├── ClienteController.java
        │   │   │   ├── ContatoController.java
        │   │   │   ├── ItemPedidoController.java
        │   │   │   ├── LicencaController.java
        │   │   │   ├── PagamentoController.java
        │   │   │   ├── PedidoController.java
        │   │   │   └── ProdutoController.java
        │   │   ├── domain/
        │   │   │   ├── cliente/    (Cliente, ClienteRepository)
        │   │   │   ├── contato/    (Contato, ContatoRepository)
        │   │   │   ├── itempedido/ (ItemPedido, ItemPedidoRepository)
        │   │   │   ├── licenca/    (Licenca, LicencaRepository)
        │   │   │   ├── pagamento/  (Pagamento, StatusPagamento, PagamentoRepository)
        │   │   │   ├── pedido/     (Pedido, StatusPedido, PedidoRepository)
        │   │   │   └── produto/    (Produto, ProdutoRepository)
        │   │   ├── dto/            (DTOs de entrada, saída e erro)
        │   │   ├── infra/          (GlobalExceptionHandler)
        │   │   ├── security/       (SecurityConfig, SecurityFilter)
        │   │   └── service/        (regras de negócio de cada domínio)
        │   └── resources/
        │       ├── application.properties
        │       └── db/migration/   (scripts Flyway V1...V8)
        └── test/
            └── java/.../SinalizaaiBackApplicationTests.java
```

---

## Modelo de domínio

| Entidade | Tabela | Descrição |
|---|---|---|
| `Cliente` | `clientes` | Empresa cliente do SinalizaAI. Identificada por CNPJ e e-mail únicos; implementa `UserDetails` para autenticação. Possui senha com hash BCrypt, dados de contato, cidade, país e data de nascimento do responsável. |
| `Produto` | `produtos` | Item do catálogo comercializado (nome, descrição, preço, flag `ativo` para soft-delete). |
| `Pedido` | `pedidos` | Pedido de um cliente, com `valorTotal` e `status` (`pendente` → `pago` / `cancelado`). |
| `ItemPedido` | `itens_pedido` | Item de um pedido (produto + quantidade + preço unitário capturado no momento da compra). |
| `Pagamento` | `pagamentos` | Registro de pagamento vinculado a um pedido: plataforma, ID de transação, bandeira/final do cartão e `status` (`pendente`, `aprovado`, `recusado`, `estornado`). Ao aprovar um pagamento, o pedido correspondente é marcado como pago automaticamente. |
| `Licenca` | `licencas` | Licença de uso do software, vinculada a um cliente e a um pedido. Chave gerada automaticamente via `UUID`, com data de início/expiração e flag `ativo`. |
| `Contato` | `contatos` | Mensagem enviada pelo formulário de contato do site, com flag `lido`. |

**Relacionamentos principais:**

```
Cliente 1─────* Pedido 1─────* ItemPedido *─────1 Produto
                  │
                  ├──────* Pagamento
                  │
Cliente 1─────* Licenca *─────1 Pedido
```

---

## Autenticação e segurança

A API utiliza **JWT (JSON Web Token)** com estratégia *stateless* (sem sessão no servidor):

1. O cliente se cadastra em `POST /api/clientes/cadastro` (senha armazenada com **BCrypt**).
2. O cliente autentica em `POST /api/auth/login`, informando `email` e `senha`.
3. Em caso de sucesso, a API retorna um **token JWT** (emissor `sinalizaai-site`, assinado com `HMAC256`, válido por **24 horas**).
4. Nas requisições subsequentes, o token deve ser enviado no cabeçalho:

   ```
   Authorization: Bearer <token>
   ```

5. O `SecurityFilter` intercepta cada requisição, valida o token e carrega o `Cliente` autenticado no contexto de segurança do Spring.

**Rotas públicas** (não exigem token):
- `POST /api/auth/login`
- `POST /api/clientes/cadastro`
- `POST /api/contatos`
- `/swagger-ui/**`, `/v3/api-docs/**`

Todas as demais rotas exigem um token JWT válido (`.anyRequest().authenticated()`).

**CORS** está configurado para aceitar requisições das seguintes origens:
- `http://localhost:3000` (CRA)
- `http://localhost:5173` (Vite)
- `https://www.sinalizaai.com`
- URL de produção do backend na Railway

---

## Documentação interativa (Swagger)

A API expõe documentação OpenAPI gerada automaticamente via `springdoc-openapi`:

| Recurso | Caminho |
|---|---|
| Swagger UI | `/swagger-ui.html` |
| Especificação OpenAPI (JSON) | `/v3/api-docs` |

Ao rodar localmente: `http://localhost:8080/swagger-ui.html`
Em produção: `https://<domínio-do-backend>/swagger-ui.html`

A tela já vem com o esquema de segurança **Bearer JWT** pré-configurado — basta clicar em *Authorize* e informar o token.

---

## Referência das APIs

Todas as rotas têm prefixo implícito conforme indicado. Corpos de requisição e resposta estão em JSON. Campos obrigatórios estão marcados com validação Bean Validation (`@NotBlank`, `@NotNull`, `@Email`, etc.), retornando `400 Bad Request` com detalhes de validação quando inválidos.

### Auth — `/api/auth`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/api/auth/login` | Pública | Autentica um cliente (e-mail + senha) e retorna um token JWT. |

**Request — `LoginDTO`**
```json
{
  "email": "contato@empresa.com",
  "senha": "minhaSenha123"
}
```

**Response — `TokenResponseDTO` (200)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Clientes — `/api/clientes`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/cadastro` | Pública | Cadastra um novo cliente (empresa). |
| `GET` | `/` | JWT | Lista todos os clientes. |
| `GET` | `/{id}` | JWT | Busca um cliente pelo ID. |
| `PUT` | `/{id}` | JWT | Atualiza dados cadastrais do cliente. |
| `DELETE` | `/{id}` | JWT | Remove o cliente do sistema. |

**Request — `CadastroClienteDTO`**
```json
{
  "razaoSocial": "Empresa Exemplo LTDA",
  "cnpj": "12.345.678/0001-90",
  "nomeResponsavel": "João da Silva",
  "email": "contato@empresa.com",
  "senha": "minhaSenha123",
  "telefone": "(11) 99999-0000"
}
```

**Request — `AtualizacaoClienteDTO`** *(todos os campos opcionais — atualização parcial)*
```json
{
  "razaoSocial": "Empresa Exemplo LTDA",
  "nomeResponsavel": "João da Silva",
  "email": "novo-email@empresa.com",
  "telefone": "(11) 98888-0000",
  "senha": "novaSenha123",
  "cidade": "São Paulo",
  "pais": "Brasil",
  "dataNascimento": "1990-05-20"
}
```

**Response — `ClienteResponseDTO`**
```json
{
  "id": 1,
  "razaoSocial": "Empresa Exemplo LTDA",
  "cnpj": "12.345.678/0001-90",
  "nomeResponsavel": "João da Silva",
  "email": "contato@empresa.com",
  "telefone": "(11) 99999-0000",
  "ativo": true,
  "cidade": "São Paulo",
  "pais": "Brasil"
}
```

> Regras de negócio: e-mail e CNPJ são únicos no sistema; a senha é sempre armazenada com hash BCrypt.

---

### Produtos — `/api/produtos`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/` | JWT | Cadastra um novo produto. |
| `GET` | `/` | JWT | Lista os produtos **ativos**. |
| `GET` | `/{id}` | JWT | Busca um produto pelo ID. |
| `PUT` | `/{id}` | JWT | Atualiza dados do produto (atualização parcial). |
| `DELETE` | `/{id}` | JWT | **Desativa** o produto (soft delete — não remove do banco). |

**Request — `CadastroProdutoDTO`**
```json
{
  "nome": "Licença SinalizaAI — Plano Mensal",
  "descricao": "Acesso completo à plataforma por 30 dias",
  "preco": 199.90
}
```

**Response — `ProdutoResponseDTO`**
```json
{
  "id": 1,
  "nome": "Licença SinalizaAI — Plano Mensal",
  "descricao": "Acesso completo à plataforma por 30 dias",
  "preco": 199.90,
  "ativo": true
}
```

---

### Pedidos — `/api/pedidos`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/` | JWT | Cria um novo pedido para um cliente. |
| `GET` | `/` | JWT | Lista todos os pedidos. |
| `GET` | `/{id}` | JWT | Busca um pedido pelo ID. |
| `GET` | `/cliente/{clienteId}` | JWT | Lista os pedidos de um cliente específico. |
| `PATCH` | `/{id}/pagar` | JWT | Marca o pedido como **pago**. |
| `PATCH` | `/{id}/cancelar` | JWT | **Cancela** o pedido. |

**Request — `CadastroPedidoDTO`**
```json
{
  "clienteId": 1,
  "produtoIds": [1, 2],
  "valorTotal": 399.80
}
```

**Response — `PedidoResponseDTO`**
```json
{
  "id": 10,
  "clienteId": 1,
  "status": "pendente",
  "valorTotal": 399.80,
  "criadoEm": "2026-06-30T14:22:00"
}
```

> Status possíveis: `pendente` → `pago` | `cancelado`.

---

### Itens do Pedido — `/api/itens-pedido`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/` | JWT | Adiciona um item (produto + quantidade) a um pedido. |
| `GET` | `/pedido/{pedidoId}` | JWT | Lista os itens de um pedido. |
| `GET` | `/{id}` | JWT | Busca um item pelo ID. |
| `DELETE` | `/{id}` | JWT | Remove um item do pedido. |

**Request — `CadastroItemPedidoDTO`**
```json
{
  "pedidoId": 10,
  "produtoId": 1,
  "quantidade": 2
}
```

**Response — `ItemPedidoResponseDTO`**
```json
{
  "id": 5,
  "pedidoId": 10,
  "produtoId": 1,
  "quantidade": 2,
  "precoUnitario": 199.90
}
```

> O `precoUnitario` é capturado a partir do preço atual do produto no momento da criação do item.

---

### Pagamentos — `/api/pagamentos`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/` | JWT | Registra um novo pagamento para um pedido. |
| `GET` | `/pedido/{pedidoId}` | JWT | Lista os pagamentos de um pedido. |
| `GET` | `/{id}` | JWT | Busca um pagamento pelo ID. |
| `PATCH` | `/{id}/aprovar` | JWT | Aprova o pagamento **e** marca o pedido correspondente como pago automaticamente. |
| `PATCH` | `/{id}/recusar` | JWT | Recusa o pagamento. |
| `PATCH` | `/{id}/estornar` | JWT | Estorna o pagamento. |

**Request — `CadastroPagamentoDTO`**
```json
{
  "pedidoId": 10,
  "plataforma": "Stripe",
  "transacaoId": "ch_3Nxxx",
  "bandeira": "Visa",
  "finalCartao": "4242",
  "valor": 399.80
}
```

**Response — `PagamentoResponseDTO`**
```json
{
  "id": 7,
  "pedidoId": 10,
  "plataforma": "Stripe",
  "transacaoId": "ch_3Nxxx",
  "bandeira": "Visa",
  "finalCartao": "4242",
  "status": "aprovado",
  "valor": 399.80,
  "criadoEm": "2026-06-30T14:25:00"
}
```

> Status possíveis: `pendente` → `aprovado` | `recusado` | `estornado`.

---

### Licenças — `/api/licencas`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/` | JWT | Gera uma nova licença para um cliente/pedido (chave `UUID` gerada automaticamente). |
| `GET` | `/` | JWT | Lista todas as licenças. |
| `GET` | `/cliente/{clienteId}` | JWT | Lista as licenças de um cliente específico. |
| `GET` | `/chave/{chave}` | JWT | Busca uma licença pela sua chave. |
| `PATCH` | `/{id}/desativar` | JWT | Desativa uma licença. |
| `PATCH` | `/{id}/ativar` | JWT | Reativa uma licença. |

**Request — `CadastroLicencaDTO`**
```json
{
  "clienteId": 1,
  "pedidoId": 10,
  "dataInicio": "2026-07-01T00:00:00",
  "dataExpiracao": "2026-08-01T00:00:00"
}
```

**Response — `LicencaResponseDTO`**
```json
{
  "id": 3,
  "clienteId": 1,
  "pedidoId": 10,
  "chave": "3f1b2a9c-7d4e-4a11-9c2e-8e0f6a7b1234",
  "ativo": true,
  "dataInicio": "2026-07-01T00:00:00",
  "dataExpiracao": "2026-08-01T00:00:00"
}
```

---

### Contatos — `/api/contatos`

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/` | Pública | Envia uma mensagem pelo formulário de contato do site. |
| `GET` | `/` | JWT | Lista todas as mensagens de contato. |
| `GET` | `/nao-lidos` | JWT | Lista as mensagens não lidas. |
| `GET` | `/lidos` | JWT | Lista as mensagens já lidas. |
| `GET` | `/{id}` | JWT | Busca uma mensagem pelo ID. |
| `PATCH` | `/{id}/lido` | JWT | Marca a mensagem como lida. |
| `DELETE` | `/{id}` | JWT | Remove uma mensagem de contato. |

**Request — `CadastroContatoDTO`**
```json
{
  "nome": "Maria Souza",
  "email": "maria@empresa.com",
  "telefone": "(11) 97777-0000",
  "mensagem": "Gostaria de mais informações sobre o SinalizaAI."
}
```

**Response — `ContatoResponseDTO`**
```json
{
  "id": 8,
  "nome": "Maria Souza",
  "email": "maria@empresa.com",
  "telefone": "(11) 97777-0000",
  "mensagem": "Gostaria de mais informações sobre o SinalizaAI.",
  "lido": false,
  "criadoEm": "2026-06-30T10:00:00"
}
```

---

## Tratamento de erros

Erros são centralizados no `GlobalExceptionHandler` e retornados em formato JSON consistente:

| Situação | HTTP Status | Corpo de resposta |
|---|---|---|
| Falha de validação (`@Valid`) | `400 Bad Request` | Lista de `ErroValidacaoDTO` (campo + mensagem) |
| Regra de negócio violada (`RuntimeException`) | `400 Bad Request` | `ErroResponseDTO { status, mensagem }` |
| Entidade não encontrada (`EntityNotFoundException`) | `404 Not Found` | `ErroResponseDTO { status, mensagem }` |
| Credenciais inválidas no login | `401 Unauthorized` | `ErroResponseDTO { status: 401, mensagem: "E-mail ou senha inválidos" }` |
| Acesso negado (token ausente/inválido para rota protegida) | `403 Forbidden` | `ErroResponseDTO { status: 403, mensagem: "Acesso negado" }` |

**Exemplo — erro de validação:**
```json
[
  {
    "campo": "email",
    "mensagem": "E-mail inválido"
  }
]
```

---

## Variáveis de ambiente

A aplicação é configurada 100% por variáveis de ambiente (sem valores sensíveis hardcoded), pensada para o ambiente **Railway**, que injeta automaticamente as credenciais do MySQL.

| Variável | Descrição |
|---|---|
| `MYSQLHOST` | Host do banco MySQL |
| `MYSQLPORT` | Porta do banco MySQL |
| `MYSQLDATABASE` | Nome do banco de dados |
| `MYSQLUSER` | Usuário do banco |
| `MYSQLPASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo usado para assinar/validar os tokens JWT (HMAC256) |
| `PORT` | Porta em que a aplicação HTTP é exposta (padrão `8080`) |

Crie um arquivo `.env` (ou exporte no shell) para rodar localmente, por exemplo:

```bash
export MYSQLHOST=localhost
export MYSQLPORT=3306
export MYSQLDATABASE=sinaliza_back
export MYSQLUSER=root
export MYSQLPASSWORD=sua_senha
export JWT_SECRET=um-segredo-bem-grande-e-aleatorio
export PORT=8080
```

---

## Executando localmente

**Pré-requisitos:** JDK 17, Maven (ou use o wrapper `./mvnw`), MySQL 8.x em execução.

```bash
# 1. Clone o repositório
git clone https://github.com/SinalizaAI/Back_end_Site.git
cd Back_end_Site/sinalizaai-back

# 2. Configure as variáveis de ambiente (ver seção acima)

# 3. Suba as migrações e a aplicação
./mvnw clean spring-boot:run
```

A API ficará disponível em `http://localhost:8080` e o Swagger UI em `http://localhost:8080/swagger-ui.html`.

---

## Executando com Docker

O projeto já inclui um `Dockerfile` multi-stage (build com Maven + imagem final apenas com o `.jar`, sobre Alpine):

```bash
cd sinalizaai-back

# Build da imagem
docker build -t sinalizaai-back .

# Execução do container
docker run -p 8080:8080 \
  -e MYSQLHOST=<host> \
  -e MYSQLPORT=<porta> \
  -e MYSQLDATABASE=<database> \
  -e MYSQLUSER=<usuario> \
  -e MYSQLPASSWORD=<senha> \
  -e JWT_SECRET=<segredo> \
  sinalizaai-back
```

---

## Migrações de banco de dados

As migrações são gerenciadas via **Flyway** (`src/main/resources/db/migration`) e aplicadas automaticamente na inicialização (`spring.flyway.enabled=true`):

| Versão | Descrição |
|---|---|
| `V1` | Criação da tabela `clientes` |
| `V2` | Criação da tabela `contatos` |
| `V3` | Criação da tabela `pedidos` |
| `V4` | Criação da tabela `produtos` |
| `V5` | Criação da tabela `itens_pedido` |
| `V6` | Criação da tabela `pagamentos` |
| `V7` | Criação da tabela `licencas` |
| `V8` | Alteração da tabela `clientes` (adição de `cidade`, `pais`, `data_nascimento`) |

Novas alterações de schema devem seguir o padrão `V{n}__descricao_da_mudanca.sql`.

---

## Deploy

- **Plataforma:** [Railway](https://railway.app)
- **Banco de dados:** MySQL provisionado pela própria Railway (variáveis injetadas automaticamente)
- **Build:** a partir do `Dockerfile` do módulo `sinalizaai-back`
- **CORS:** liberado explicitamente para o domínio de produção do site (`https://www.sinalizaai.com`) e para a própria URL pública do backend

---

## Testes

O projeto contém a estrutura padrão de testes do Spring Boot (`spring-boot-starter-test` e `spring-security-test`):

```bash
./mvnw test
```

> A suíte de testes atual contempla o teste de contexto (`SinalizaaiBackApplicationTests`); há espaço para expansão com testes unitários de `service` e testes de integração dos `controller`.

---

## Roadmap / observações técnicas

Pontos identificados no código atual que podem orientar próximas evoluções:

- A entidade `Cliente` possui um método `desativar()` (soft delete), mas o endpoint `DELETE /api/clientes/{id}` hoje executa remoção definitiva do registro — vale alinhar o comportamento caso o soft delete seja o padrão desejado para clientes.
- `CadastroPedidoDTO` recebe `produtoIds` e `valorTotal` diretamente; o cálculo do valor total a partir dos itens (via `ItemPedidoService`) pode ser centralizado no `PedidoService` para evitar inconsistência entre o valor informado e a soma real dos itens.
- Ainda não há um mecanismo de *refresh token* — o JWT expira em 24h e exige novo login.
- A associação entre `produtoIds` do pedido e os `ItemPedido` reais não é automática no `POST /api/pedidos` — a criação dos itens é feita separadamente via `POST /api/itens-pedido`.

---

## Contribuindo

1. Crie um branch a partir da `main`: `git checkout -b feature/minha-feature`.
2. Siga o padrão de camadas existente (`controller` → `service` → `domain`/`repository`) e o uso de DTOs para entrada/saída.
3. Adicione migrações Flyway para qualquer alteração de schema.
4. Documente novos endpoints com as anotações `@Tag`/`@Operation` do Swagger.
5. Abra um Pull Request descrevendo a mudança.

---

## Autor

Desenvolvido por **Kauê Siqueira** <br/>
GitHub: [@KaueSiqueira54](https://github.com/KaueSiqueira54)

---

<p align="center">Desenvolvido para a plataforma <strong>SinalizaAI</strong> — <a href="https://www.sinalizaai.com">sinalizaai.com</a></p>
