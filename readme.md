# Projeto Planner com Spring Boot e SQL Server - NLW Rocketseat 

Este é o Planner utilizando Spring Boot com SQL Server como banco de dados, configurado via Docker Compose, para o NLW da Rocketseat na trilha de Java

## Pré-requisitos

Antes de começar, você vai precisar ter as seguintes ferramentas instaladas em sua máquina:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [JDK 21+](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/install.html)

## Configuração

### Docker Compose

O arquivo `docker-compose.yml` configura um contêiner com o SQL Server. Certifique-se de que o Docker está em execução e, em seguida, inicie o contêiner do SQL Server com o seguinte comando:

```bash
docker-compose up -d
