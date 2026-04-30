# API Dígito Único

Serviço REST para cálculo de dígito único com criptografia RSA, cache personalizado e CRUD de usuários. Construído com Spring Boot 3.2, Java 21 e banco de dados H2 em memória.

## O que é o Dígito Único

**Definição recursiva:**
- Se `x` tem apenas um dígito → o dígito único é `x`
- Caso contrário → `digito_unico(x) = digito_unico(soma dos dígitos de x)`

Dados `n` e `k`, forma-se `P` concatenando `n` exatamente `k` vezes e calcula-se `digito_unico(P)`.

**Exemplo:** `n = "9875"`, `k = 4`
```
P = 9875987598759875
soma dos dígitos de P → 5+7+8+9+5+7+8+9+5+7+8+9+5+7+8+9 = 116
digito_unico(116) → 1+1+6 = 8
digito_unico(8) = 8  ← resultado final
```

> **Otimização:** em vez de concatenar `n` k vezes, o algoritmo calcula `soma_digitos(n) × k` e reduz o resultado — matematicamente equivalente e eficiente para valores grandes de `n` e `k`.

## Fluxo de Criptografia RSA

```
Cliente gera par de chaves RSA (2048 bits)
              ↓
POST /api/usuarios/chave-publica  → envia chave pública → retorna { id }
              ↓
POST /api/usuarios                → envia { id, nome, email } → dados gravados criptografados
              ↓
POST /api/usuarios/{id}/descriptografar → envia chave privada → retorna { nome, email } em texto claro
```

> A chave privada **nunca é armazenada** no servidor. Apenas o cliente a possui.

## Cache

Os resultados são armazenados em um cache LRU (Least Recently Used) com capacidade de **10 entradas**. Quando o cache está cheio, a entrada menos recentemente usada é removida automaticamente. O campo `fromCache` na resposta indica se o resultado veio do cache.

## Tecnologias

- Java 21
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (em memória)
- Springdoc OpenAPI (Swagger)
- Maven

## Como Executar

### Pré-requisitos

- Java 21+
- Maven 3.6+

### Passos

```bash
# 1. Clonar o repositório
git clone https://github.com/Sampaio-Thiago/digito-unico-api.git
cd digito-unico-api

# 2. Compilar e executar
mvn spring-boot:run
```

Ou via JAR:

```bash
mvn clean package
java -jar target/digito-unico-api-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em `http://localhost:8080`.

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |

> H2 Console — JDBC URL: `jdbc:h2:mem:digitounico` · Username: `sa` · Password: `password`

## Testes Unitários

O projeto possui testes unitários cobrindo controllers e serviços:

```
src/test/java/com/inter/digitounico/
├── controller/
│   └── CalculoControllerTest.java
└── service/
    ├── CacheServiceTest.java
    ├── CriptografiaServiceTest.java
    └── DigitoUnicoServiceTest.java
```

### Executar todos os testes

```bash
mvn test
```

### Executar um teste específico

```bash
# Por classe
mvn test -Dtest=DigitoUnicoServiceTest

# Por método
mvn test -Dtest=DigitoUnicoServiceTest#nomeDoMetodo
```

### Executar testes com relatório de cobertura

```bash
mvn test surefire-report:report
```

O relatório HTML será gerado em `target/site/surefire-report.html`.

## Endpoints da API

### Dígito Único

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/calculos/digito-unico` | Calcular dígito único |
| `GET` | `/api/calculos/usuario/{usuarioId}` | Buscar cálculos de um usuário |

### Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/usuarios/chave-publica` | **Passo 1** — Registrar chave pública RSA (cria o usuário) |
| `POST` | `/api/usuarios` | **Passo 2** — Salvar nome e email (criptografados) |
| `POST` | `/api/usuarios/{id}/descriptografar` | Descriptografar dados com a chave privada do cliente |
| `GET` | `/api/usuarios` | Listar todos os usuários |
| `GET` | `/api/usuarios/{id}` | Buscar usuário por ID |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/api/usuarios/{id}` | Deletar usuário |

## Exemplos de Uso

### Criar um usuário

A criação de usuário exige criptografia RSA e segue **dois passos obrigatórios**.

**Passo 1 — Gerar um par de chaves RSA e extrair a chave pública em Base64:**

```bash
# Gerar chave privada
openssl genrsa -out privada.pem 2048

# Extrair chave pública em Base64 (formato X.509/DER)
openssl rsa -in privada.pem -pubout -outform DER | base64 -w 0 > publica.b64
```

**Passo 2 — Registrar a chave pública** (cria o usuário no banco e retorna o `id`):

```bash
curl -X POST http://localhost:8080/api/usuarios/chave-publica \
  -H "Content-Type: application/json" \
  -d "{\"chavePublica\": \"$(cat publica.b64)\"}"
```

Exemplo de resposta:
```json
{ "id": 1, "chavePublica": "..." }
```

**Passo 3 — Salvar nome e email** (usar o `id` retornado no passo anterior):

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "nome": "João Silva", "email": "joao@example.com"}'
```

> Os dados são armazenados criptografados com a chave pública RSA fornecida.

---

### Calcular dígito único

```bash
curl -X POST http://localhost:8080/api/calculos/digito-unico \
  -H "Content-Type: application/json" \
  -d '{"n": "9875", "k": 4}'
```

Resposta esperada:
```json
{ "n": "9875", "k": 4, "digitoUnico": 8, "fromCache": false }
```

Repetindo a mesma chamada, `fromCache` será `true`.

### Buscar cálculos de um usuário

```bash
curl http://localhost:8080/api/calculos/usuario/1
```
