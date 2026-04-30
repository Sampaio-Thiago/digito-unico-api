# API Dígito Único

Esta aplicação implementa um serviço REST para cálculo de dígito único com funcionalidades de criptografia RSA, cache personalizado e CRUD de usuários.

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.6+

### Compilação e Execução

#### 1. Clonar o repositório
```bash
git clone <url-do-repositorio>
cd digito-unico-api
```

#### 2. Compilar a aplicação
```bash
mvn clean compile
```

#### 3. Executar os testes
```bash
mvn test
```

#### 4. Gerar o JAR da aplicação
```bash
mvn clean package
```

#### 5. Executar a aplicação

**Opção 1: Usando Maven**
```bash
mvn spring-boot:run
```

**Opção 2: Usando o JAR gerado**
```bash
java -jar target/digito-unico-api-0.0.1-SNAPSHOT.jar
```

#### 6. Acessar a aplicação
- **API Base URL**: `http://localhost:8080`
- **Documentação Swagger**: `http://localhost:8080/swagger-ui/index.html`
- **Console H2 Database**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (deixar em branco)

## 📋 Endpoints da API

### Cálculos de Dígito Único
- `POST /api/calculos/digito-unico` - Calcular dígito único
- `GET /api/calculos/usuario/{usuarioId}` - Buscar cálculos de um usuário

### CRUD de Usuários
- `GET /api/usuarios` - Listar todos os usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID
- `POST /api/usuarios` - Criar novo usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário
- `POST /api/usuarios/chave-publica` - Enviar chave pública RSA

### Exemplo de uso

#### Criar um usuário
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com"
  }'
```

#### Calcular dígito único
```bash
curl -X POST http://localhost:8080/api/calculos/digito-unico \
  -H "Content-Type: application/json" \
  -d '{
    "n": "9875",
    "k": 4,
    "usuarioId": 1
  }'
```</llm-patch>
