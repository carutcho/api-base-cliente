# API de Consulta de crédito

Esta é uma API desenvolvida em Spring Boot para gerenciamento consulta de credito de clientes.  
A aplicação utiliza PostgreSQL como banco de dados e está preparada para rodar em diferentes ambientes utilizando Docker.

## Tecnologias Utilizadas

- **Java 11** (compilado e executado com Maven e OpenJDK)
- **Spring Boot 2.7.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Springfox Swagger 2.9.2** – para documentação da API
- **Docker & Docker Compose**

## Estrutura do Projeto

- **`src/main/java`**: Código-fonte da aplicação.
- **`src/main/resources`**: Configurações (application.properties, application-DEV.properties, etc.).
- **`pom.xml`**: Gerenciamento de dependências e build com Maven.
- **`docker/`**: Arquivos de configuração do Docker Compose por ambiente.
- **`Dockerfile`**: Dockerfile padrão para produção e homologação, **sem debug remoto**.
- **`Dockerfile_local`**: Dockerfile específico para desenvolvimento, **com debug remoto habilitado na porta 5005**.

## Configuração do Banco de Dados

A aplicação utiliza PostgreSQL.  
As configurações de conexão (URL, usuário e senha) estão definidas no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://db:5432/base_consulta_credito
spring.datasource.username=appuser
spring.datasource.password=apppassword
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
```
Ao rodar a aplicação localmente (fora do Docker), ajuste a URL para:

```
 jdbc:postgresql://localhost:5432/base_consulta_credito
 ```
## Massa de Dados (Mock)

Para facilitar testes, o arquivo sql/data.sql na raiz do projeto contém as instruções SQL de INSERT com massa de dados para a tabela credito.Ele inclui registros de exemplo conforme o enunciado e dados adicionais para cobertura de cenário.

## Como Rodar o Projeto com Docker

O projeto possui dois Dockerfiles e arquivos de Docker Compose para ambientes distintos:

- **Dockerfile (Padrão)** – Utilizado para ambientes de produção e homologação, sem debug remoto.
- **Dockerfile_local** – Utilizado para o ambiente de desenvolvimento, com debug remoto ativado na porta 5005.

## Ambiente de Desenvolvimento (com Debug Remoto)

Para rodar o ambiente de desenvolvimento com debug remoto habilitado, execute:

```
docker-compose -f docker/docker-compose-develop.yml up --build
```

Esse comando irá:
- Construir a imagem utilizando o **Dockerfile_local**.
- Subir os containers do backend e do PostgreSQL.
- Mapear as portas **8080** (API) e **5005** (Debug).

## Configuração de Debug Remoto (IntelliJ IDEA ou VS Code)

No IntelliJ IDEA:
- Vá em **Run → Edit Configurations...**
- Adicione uma nova configuração do tipo **Remote JVM Debug**.
- Configure:
    - **Host:** `localhost`
    - **Porta:** `5005`
    - **Transport:** `Socket`
- Salve a configuração e inicie o debug.
- Faça uma requisição via Postman para testar os breakpoints.

## Ambiente de Produção/Homologação (sem Debug)

Para rodar o ambiente sem debug remoto, execute:

```
docker-compose -f docker/docker-compose-prd.yml up --build
```

Esse comando utiliza o **Dockerfile** padrão e subirá os containers no ambiente configurado.

## Acessando a Aplicação

- **Backend:**  
  A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

- **PostgreSQL:**  
  O banco de dados estará acessível em: `localhost:5432`  
  (usuário: `appuser`, senha: `apppassword`)

## Documentação da API (Swagger)

Após a aplicação estar rodando, a documentação interativa da API pode ser acessada em:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Debug Remoto

O **Dockerfile_local** está configurado para habilitar o debug remoto na porta **5005**.  
Configure sua IDE para conectar via Remote Debug para `localhost:5005`.

## Problemas Comuns e Soluções
Banco sem dados: execute manualmente o data.sql ou use o mapeamento em volumes.

Conexão recusada: ajuste a URL do datasource para usar localhost em vez de db.

### Erro de Conexão com o Banco

Se estiver executando localmente, ajuste a URL do datasource para usar `localhost` em vez de `db`.


