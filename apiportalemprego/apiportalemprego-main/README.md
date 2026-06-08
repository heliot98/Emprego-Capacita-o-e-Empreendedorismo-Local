# Portal Emprego API

API REST para gerenciamento de vagas de emprego e cursos de capacitação, com autenticação JWT.

---

## Tecnologias Utilizadas

| Tecnologia | Descrição |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot | Framework principal |
| Spring Security | Autenticação e autorização |
| JWT (jjwt 0.12) | Geração e validação de tokens |
| Spring Data JPA | Persistência de dados |
| PostgreSQL | Banco de dados relacional |
| Lombok | Redução de código boilerplate |
| Bean Validation | Validação de entradas |
| Spring Actuator | Monitoramento da aplicação |

---

## Configuração do Ambiente

### Pré-requisitos

- Java 17 instalado
- PostgreSQL instalado e rodando
- Maven ou acesso ao wrapper `./mvnw`

### Banco de Dados

Crie o banco de dados no PostgreSQL:

```sql
CREATE DATABASE backendav2;
```

### Variáveis de Configuração (`application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/backendav2
spring.datasource.username=postgres
spring.datasource.password=gerentedb

jwt.secret=PortalEmprego2026ChaveSecretaJWTSuperSeguraHS256
jwt.expiration=86400000
```

> O `jwt.secret` deve ter no mínimo 32 caracteres.  
> O `jwt.expiration` é em milissegundos (86400000 = 24 horas).

### Executar a Aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## Autenticação com JWT

Esta API usa **JSON Web Token (JWT)** para proteger as rotas privadas.

### Como funciona?

1. O usuário se cadastra em `POST /auth/register`
2. O usuário faz login em `POST /auth/login` e recebe um **token JWT**
3. Para acessar rotas privadas, o token deve ser enviado no **header** de cada requisição:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

O token tem validade de **24 horas**. Após expirar, é necessário fazer login novamente.

---

## Como Cadastrar um Usuário

**Endpoint:** `POST /auth/register`  
**Rota pública** (não precisa de token)

### Requisição

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123"
}
```

### Resposta (HTTP 201 Created)

```
Usuário 'João Silva' cadastrado com sucesso!
```

### Regras
- O `email` deve ser único — não é possível cadastrar dois usuários com o mesmo email
- A `senha` deve ter no mínimo **6 caracteres**
- A senha é salva no banco como **hash BCrypt** (nunca em texto puro)

### Exemplo de Erro — Email Duplicado (HTTP 409)

```json
{
  "timestamp": "2026-06-08T10:00:00",
  "status": 409,
  "erro": "Conflito de dados",
  "mensagens": ["Já existe um usuário cadastrado com o email: joao@email.com"]
}
```

---

## Como Fazer Login

**Endpoint:** `POST /auth/login`  
**Rota pública** (não precisa de token)

### Requisição

```json
{
  "email": "joao@email.com",
  "senha": "senha123"
}
```

### Resposta (HTTP 200 OK)

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQGVtYWlsLmNvbSIsImlhdCI6MTc0OTM2NjQwMCwiZXhwIjoxNzQ5NDUyODAwfQ.assinatura"
}
```

### Exemplo de Erro — Credenciais Inválidas (HTTP 401)

```json
{
  "timestamp": "2026-06-08T10:00:00",
  "status": 401,
  "erro": "Credenciais invalidas",
  "mensagens": ["Email ou senha incorretos."]
}
```

---

## Como Usar o Token JWT nas Requisições

Após o login, copie o token retornado e envie no **header** de todas as requisições privadas:

### No Postman

1. Abra a requisição desejada
2. Vá na aba **Authorization**
3. Selecione o tipo **Bearer Token**
4. Cole o token no campo **Token**

### Manualmente no Header

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

### Exemplo de Erro — Token Ausente ou Inválido (HTTP 401)

```json
{
  "erro": "Acesso negado. Faça login e envie o token JWT no header Authorization: Bearer <token>"
}
```

---

## Mapa de Rotas

### Rotas Públicas (sem token)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/register` | Cadastra um novo usuário |
| POST | `/auth/login` | Faz login e retorna o token JWT |
| GET | `/status` | Verifica se a API está online |
| GET | `/vagas` | Lista todas as vagas |
| GET | `/vagas/{id}` | Busca uma vaga por ID |
| GET | `/cursos` | Lista todos os cursos |
| GET | `/cursos/{id}` | Busca um curso por ID |

### Rotas Privadas (requerem token JWT)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/vagas` | Cadastra uma nova vaga |
| PUT | `/vagas/{id}` | Atualiza uma vaga existente |
| DELETE | `/vagas/{id}` | Remove uma vaga |
| POST | `/cursos` | Cadastra um novo curso |
| PUT | `/cursos/{id}` | Atualiza um curso existente |
| DELETE | `/cursos/{id}` | Remove um curso |
| GET | `/usuarios` | Lista todos os usuários cadastrados |

---

## Exemplos de Requisições

### Criar uma Vaga (privado)

**Header:**
```
Authorization: Bearer <seu_token_aqui>
Content-Type: application/json
```

**Body:**
```json
{
  "titulo": "Desenvolvedor Java Júnior",
  "descricao": "Vaga para desenvolvedor Java com Spring Boot",
  "empresa": "Tech Solutions",
  "salario": 3500.00,
  "localizacao": "São Paulo - SP"
}
```

**Resposta (HTTP 201 Created):**
```json
{
  "id": 1,
  "titulo": "Desenvolvedor Java Júnior",
  "descricao": "Vaga para desenvolvedor Java com Spring Boot",
  "empresa": "Tech Solutions",
  "salario": 3500.00,
  "localizacao": "São Paulo - SP"
}
```

---

### Cadastrar um Curso (privado)

**Header:**
```
Authorization: Bearer <seu_token_aqui>
Content-Type: application/json
```

**Body:**
```json
{
  "titulo": "Desenvolvimento Web com Spring Boot",
  "descricao": "Aprenda a criar APIs REST com Java e Spring Boot",
  "instituição": "Alura",
  "cargaHoraria": 40,
  "modalidade": "EAD"
}
```

---

### Listar Usuários (privado)

**Header:**
```
Authorization: Bearer <seu_token_aqui>
```

**Resposta (HTTP 200 OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@email.com",
    "role": "USER"
  }
]
```

> A senha **nunca** é retornada nas respostas da API.

---

## Estrutura do Projeto

```
src/main/java/com/portal/emprego/
├── controller/
│   ├── AuthController.java       # POST /auth/register e /auth/login
│   ├── CursoController.java      # CRUD de cursos
│   ├── StatusController.java     # GET /status (healthcheck)
│   ├── UsuarioController.java    # GET /usuarios (privado)
│   └── VagaController.java       # CRUD de vagas
├── dto/
│   ├── ErroRespostaDTO.java      # Formato padrão de erros
│   ├── LoginRequestDTO.java      # Dados de entrada do login
│   ├── LoginResponseDTO.java     # Resposta com o token JWT
│   ├── RegisterRequestDTO.java   # Dados de cadastro de usuário
│   └── VagaRequestDTO.java       # Dados de criação/atualização de vaga
├── exception/
│   ├── GlobalExceptionHandler.java        # Trata todas as exceções da API
│   └── RecursoNaoEncontradoException.java # Exceção para 404
├── model/
│   ├── Curso.java    # Entidade curso
│   ├── Role.java     # Enum: USER, ADMIN
│   ├── Usuario.java  # Entidade usuário (implementa UserDetails)
│   └── Vaga.java     # Entidade vaga
├── repository/
│   ├── CursoRepository.java    # Acesso ao banco para cursos
│   ├── UsuarioRepository.java  # Acesso ao banco para usuários
│   └── VagaRepository.java     # Acesso ao banco para vagas
├── security/
│   ├── JwtAuthenticationFilter.java  # Filtro que valida o JWT em cada requisição
│   ├── JwtUtil.java                  # Gera e valida tokens JWT
│   ├── SecurityConfig.java           # Configuração do Spring Security
│   └── UsuarioDetailsService.java    # Carrega usuário do banco para o Spring Security
└── service/
    ├── CursoService.java    # Regras de negócio de cursos
    ├── UsuarioService.java  # Cadastro de usuários com BCrypt
    └── VagaService.java     # Regras de negócio de vagas
```

---

## Segurança

- **BCrypt**: as senhas são armazenadas como hash irreversível. Mesmo que o banco seja comprometido, as senhas originais não podem ser recuperadas.
- **JWT Stateless**: o servidor não armazena sessões. Cada requisição é autenticada pelo token.
- **Rotas protegidas por padrão**: qualquer rota não mapeada explicitamente requer autenticação.
- **Token com expiração**: tokens expiram em 24h, limitando o impacto de tokens vazados.
- **Senha nunca exposta**: o campo senha tem `@JsonIgnore` na entidade, nunca aparecendo nas respostas.
