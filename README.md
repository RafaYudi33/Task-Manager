# To Do List API

Uma API REST simples para gerenciar tarefas pessoais.

## Visão Geral

Esta API permite a criação, leitura e atualização de tarefas em uma lista de afazeres. Foi construída com Spring Boot e utiliza o banco de dados H2 para armazenar as tarefas.


### Recursos Usuários
:ballot_box_with_check:Cadastrar novos usuários


| Método HTTP | Endpoint       | Descrição                                 |
|-------------|----------------|-------------------------------------------|
|POST         | /users/        | Cadastra um novo Usuário                  |








### Recursos Tarefas
:ballot_box_with_check:Buscar todas as tarefas 

:ballot_box_with_check:Alterar uma tarefa de maneira parcial ou completa (Autenticação de usuario para alteração)

:ballot_box_with_check:Listar todas as tarefas de um usuário 

:ballot_box_with_check:Cadastrar novas tarefas

| Método HTTP | Endpoint       | Descrição                                 |
|-------------|----------------|-----------------------------------------|
| GET         | /tasks/        | Retorna todas as tarefas cadastradas    |
| GET         | /tasks/id      | Retorna lista de tarefas do usuário (IdUser passado no Basic Auth)  |
| POST        | /tasks/        | Cadastra uma nova tarefa                |
| PUT         | /tasks/id      | Atualiza uma tarefa existente (Autenticação do usuário pelo Basic Auth)   |
| DELETE      | /tasks/id      | Exclui uma tarefa existente  (Autenticação do usuário pelo Basic Auth)    |




## Requisitos

Certifique-se de atender aos seguintes requisitos antes de usar a API:

:information_source:Java 17 ou superior  
:information_source:Maven para gerenciamento de dependências

