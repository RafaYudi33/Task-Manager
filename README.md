# To Do List API

Uma API REST simples para gerenciar tarefas pessoais.

## Visão Geral

Esta API permite a criação, leitura e atualização de tarefas em uma lista de afazeres. Foi construída com Spring Boot e utiliza o banco de dados H2 para armazenar as tarefas.

## Recursos

A seguir, estão os principais recursos oferecidos por esta API:

| Método HTTP | Endpoint       | Descrição                                 |
|-------------|----------------|-----------------------------------------|
| GET         | /tasks         | Retorna todas as tarefas cadastradas    |
| GET         | /task/:id      | Retorna uma tarefa pelo ID do usuário   |
| POST        | /task          | Cadastra uma nova tarefa                |
| PUT         | /task/:id      | Atualiza uma tarefa existente           |
| DELETE      | /task/:id      | Exclui uma tarefa existente pelo ID     |

## Requisitos

Certifique-se de atender aos seguintes requisitos antes de usar a API:

- Java 17 ou superior
- Maven para gerenciamento de dependências

