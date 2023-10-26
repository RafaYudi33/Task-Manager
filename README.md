# To Do List API

Uma API REST para gerenciar tarefas pessoais.

## Automação

A automação desta API é ativada todos os dias às 18:00 e envia e-mails para os usuários que possuem tarefas próximas do fim. Para esta automação, foi utilizado o serviço Simple Email Service (SES) da AWS. No período de testes, somente os e-mails que estão cadastrados no SES funcionam para a automação.

## Visão Geral

Esta API permite a criação, leitura e atualização de tarefas em uma lista de afazeres. Foi construída com Spring Boot e utiliza o banco de dados H2 para armazenar as tarefas.

## Recursos Usuários

:ballot_box_with_check: Cadastrar novos usuários

| Método HTTP | Endpoint       | Descrição                                 |
|-------------|----------------|-------------------------------------------|
| POST        | /users/        | Cadastra um novo Usuário                  |

## Recursos Tarefas

:ballot_box_with_check: Buscar todas as tarefas


:ballot_box_with_check: Alterar uma tarefa de maneira parcial ou completa


:ballot_box_with_check: Listar todas as tarefas de um usuário


:ballot_box_with_check: Cadastrar novas tarefas


:ballot_box_with_check: Deletar tarefas

| Método HTTP | Endpoint       | Descrição                                 |
|-------------|----------------|-----------------------------------------|
| GET         | /tasks/        | Retorna todas as tarefas cadastradas    |
| GET         | /tasks/id      | Retorna lista de tarefas do usuário (c/ Autenticação de Usuário)  |
| POST        | /tasks/        | Cadastra uma nova tarefa (c/ Autenticação de Usuário)               |
| PUT         | /tasks/id      | Atualiza uma tarefa existente (c/ Autenticação de Usuário)   |
| DELETE      | /tasks/id      | Exclui uma tarefa existente  (c/ Autenticação de Usuário)    |

OBS: Todas as autenticações foram feitas através do Usuário e Senha sendo passados no Basic Auth da requisição.

## Requisitos

Certifique-se de atender aos seguintes requisitos antes de usar a API:

:information_source: Java 17 ou superior
:information_source: Maven para gerenciamento de dependências
