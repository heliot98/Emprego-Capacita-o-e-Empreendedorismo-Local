# Backend: Portal de Emprego e Capacitação

## Descrição do Projeto
API desenvolvida em Java para conectar talentos a oportunidades de trabalho e cursos de aperfeiçoamento profissional, incentivando o empreendedorismo local.

## Objetivo
Resolver a dificuldade de centralizar vagas de emprego e trilhas de aprendizado em uma única plataforma.

## Tecnologias Usadas
- Java 17+
- Spring Boot
- MySQL / PostgreSQL
- Maven
- Swagger (Documentação)

## Como Rodar o Projeto
1. Clone o repositório: `git clone [link]`
2. Configure o banco de dados no arquivo `application.properties`.
3. Execute o comando: `./mvnw spring-boot:run`

## Endpoints Principais
- `GET /vagas`: Lista todas as vagas disponíveis.
- `POST /usuarios/cadastro`: Registra um novo candidato ou empreendedor.
- `GET /cursos`: Lista cursos de capacitação.

## Sistema de Empregabilidade

Projeto backend voltado para:
- Busca de vagas
- Cadastro de empresas
- Cursos de capacitação
- Empreendedorismo local


## Diagrama do Banco de Dados

```mermaid
erDiagram

    USUARIO ||--o{ CANDIDATURA : realiza
    EMPRESA ||--o{ VAGA : publica
    USUARIO ||--o{ INSCRICAO_CURSO : participa
    CURSO ||--o{ INSCRICAO_CURSO : possui
    VAGA ||--o{ CANDIDATURA : recebe
    USUARIO ||--o{ NOTIFICACAO : recebe
    USUARIO ||--o{ EMPREENDEDORISMO : cria

    USUARIO {
        int ID_Usuario PK
        string NomeCompleto
        string CPF
        string Email
        string Senha
        string Telefone
        string TipoUsuario
    }

    EMPRESA {
        int ID_Empresa PK
        string NomeEmpresa
        string CNPJ
        string Email
        string Telefone
        string Endereco
        string Descricao
    }

    VAGA {
        int ID_Vaga PK
        string Titulo
        string Descricao
        string Requisitos
        decimal Salario
        string Localizacao
        string TipoContrato
        string Modalidade
        string StatusVaga
    }

    CANDIDATURA {
        int ID_Candidatura PK
        int ID_Usuario FK
        int ID_Vaga FK
        datetime DataCandidatura
        string StatusCandidatura
    }

    CURSO {
        int ID_Curso PK
        string Titulo
        string Categoria
        int CargaHoraria
        string Nivel
        string Instituicao
    }

    INSCRICAO_CURSO {
        int ID_Inscricao PK
        int ID_Usuario FK
        int ID_Curso FK
        datetime DataInscricao
        string StatusInscricao
    }

    EMPREENDEDORISMO {
        int ID_Projeto PK
        string NomeProjeto
        string Categoria
        string Localizacao
        string Descricao
    }

    NOTIFICACAO {
        int ID_Notificacao PK
        int ID_Usuario FK
        string Titulo
        string Mensagem
        datetime DataEnvio
        boolean StatusLeitura
    }

## Integrantes
- Daniel Duarte (01847432)
- Erik Menezes (01848032)
- Hélio Tarquino (01548379)
- Kilderys Kallayb (01274330)
- Wesley gonçalves (01849581)
- Thales Tadeu (01857106)
