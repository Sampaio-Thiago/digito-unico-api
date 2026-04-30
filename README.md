# API Dígito Único

Serviço REST para cálculo de dígito único com criptografia RSA, cache personalizado e CRUD de usuários. Construído com Spring Boot 3.2, Java 21 e banco de dados H2 em memória.

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
| `GET` | `/api/usuarios` | Listar todos os usuários |
| `GET` | `/api/usuarios/{id}` | Buscar usuário por ID |
| `POST` | `/api/usuarios` | Criar novo usuário |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/api/usuarios/{id}` | Deletar usuário |
| `POST` | `/api/usuarios/chave-publica` | Enviar chave pública RSA |

## Exemplos de Uso

### Criar um usuário

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "João Silva", "email": "joao@example.com"}'
```

### Calcular dígito único

```bash
curl -X POST http://localhost:8080/api/calculos/digito-unico \
  -H "Content-Type: application/json" \
  -d '{"n": "9875", "k": 4, "usuarioId": 1}'
```
