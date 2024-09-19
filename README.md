# TaskMaster API

**TaskMaster API** é uma solução de gerenciamento de tarefas desenvolvida com Java e Spring Boot. Esta API RESTful permite aos usuários criar, atualizar, visualizar e remover tarefas, além de categorizar e definir prazos para elas. É ideal para aplicações web e móveis, e pode ser integrada com outras ferramentas de produtividade.

## Funcionalidades

- **Cadastro de Tarefas**: Crie novas tarefas com título, descrição, categoria, data de vencimento e status.
- **Listagem de Tarefas**: Liste todas as tarefas com filtros para categoria, status e prazo.
- **Detalhes da Tarefa**: Visualize detalhes de uma tarefa específica.
- **Atualização de Tarefa**: Atualize informações de uma tarefa existente.
- **Remoção de Tarefa**: Remova tarefas que não são mais necessárias.
- **Filtragem e Pesquisa**: Pesquise tarefas por título, descrição, categoria ou data de vencimento.
- **Autenticação e Autorização**: Proteja endpoints com autenticação JWT para garantir que apenas usuários autenticados possam criar, atualizar ou remover tarefas.

## Tecnologias

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para construção da API RESTful.
- **Spring Data JPA**: ORM para acesso ao banco de dados.
- **Banco de Dados**: H2 (para desenvolvimento) ou PostgreSQL/MySQL (para produção).
- **Swagger/OpenAPI**: Documentação da API.
- **JUnit/Mockito**: Testes unitários e de integração.

## Documentação

**Swagger** - A API está totalmente documentada via Swagger, com cada endpoint comentado com sua descrição. Para acessar, basta rodar o projeto e acessar o link:

```bash
localhost:8080/swagger-ui
```

## Instalação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/taskmaster-api.git
