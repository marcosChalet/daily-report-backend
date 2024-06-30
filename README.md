# Projeto ToDoList com Spring Boot e React com TypeScript

Este é um projeto de uma aplicação Todo List desenvolvida utilizando Spring Boot no backend e React com TypeScript no frontend. A aplicação permite ao usuário criar, visualizar, atualizar e excluir tarefas de listas.

![image](https://github.com/marcosChalet/todolist-java-react/assets/72557256/80aa8989-646c-4b50-9965-fd72c2204cdf)
![image](https://github.com/marcosChalet/todolist-java-react/assets/72557256/a2dcfb8e-1c9d-4408-9eea-18615d0152e8)

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- Java Development Kit (JDK) 17 ou superior
- Git

**Deve-se gerar a chave pública e privada em src/main/resources:**

```
openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
```

```
openssl rsa -pubout -in app.key -out app.pub
```


**_Para rodar o projeto execute:_**

```
docker compose up -d
```

Após isso o backend estará sendo executado na porta 8080.

## Uso

Ao acessar a aplicação, você poderá visualizar as listas de tarefas existentes, adicionar novas tarefas e excluí-las.

## Estrutura do Projeto

O projeto está dividido em duas partes principais: o backend e o frontend.

### Backend

O backend foi desenvolvido utilizando o framework Spring Boot e implementa uma API RESTful para manipulação das tarefas. O código fonte do backend está localizado no diretório `backend`.

A estrutura de pacotes do `backend > api` segue a convenção do Spring Boot:

- `com.mchalet.todoapp`: pacote raiz do projeto
- `com.mchalet.todoapp.controller`: contém os controladores REST para a API
- `com.mchalet.todoapp.model`: define os modelos de dados da aplicação
- `com.mchalet.todoapp.repository`: implementa as operações de acesso aos dados
- `com.mchalet.todoapp.service`: contém as classes de serviço para manipulação das tarefas
