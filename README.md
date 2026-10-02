# Sistema de Cadastro de Hospedagens

Sistema desktop desenvolvido em Java para cadastro e gerenciamento de hospedagens em um hotel.

O projeto foi desenvolvido como parte de uma avaliação da disciplina de Programação Orientada a Objetos, com o objetivo de aplicar conceitos de orientação a objetos, persistência de dados e desenvolvimento de uma aplicação com interface gráfica.

## Funcionalidades

* Cadastro de hospedagens
* Consulta de hospedagens cadastradas
* Atualização de hospedagens
* Exclusão de hospedagens
* Consulta do hóspede, quarto, data de entrada e data de saída
* Persistência dos dados em banco de dados MySQL
* Validação dos dados informados pelo usuário

## Tecnologias utilizadas

* Java 21
* Java Swing
* JDBC
* MySQL
* Maven
* IntelliJ IDEA
* DBeaver

## Organização do projeto

* model: contém as classes que representam os dados do sistema.
* repository: responsável pelas operações de acesso e manipulação dos dados no banco.
* database: responsável pela conexão com o MySQL.
* gui: contém a interface gráfica desenvolvida com Java Swing.
* Main: responsável pela inicialização da aplicação.

## Banco de dados

O sistema utiliza um banco de dados MySQL chamado `sistema_hotel`.

A tabela principal é `hospedagens`, contendo:

* ID
* Nome do hóspede
* Número do quarto
* Data de entrada
* Data de saída

## Configuração

Para executar o projeto, é necessário possuir:

* JDK 21 ou superior
* Maven
* MySQL
* IntelliJ IDEA ou outra IDE compatível com projetos Maven

Após configurar o banco de dados, o projeto pode ser executado pela classe Main.java

## Uso de Inteligência Artificial

Durante o desenvolvimento deste projeto, foi utilizado auxílio de Inteligência Artificial como ferramenta de apoio, principalmente na aprendizagem e implementação da interface gráfica utilizando Java Swing, tecnologia que ainda não havia sido estudada previamente.

A IA foi utilizada como recurso de suporte para compreender conceitos, solucionar dúvidas e auxiliar na implementação e integração dos componentes do sistema.

O projeto também foi utilizado como oportunidade de estudo e aplicação prática dos conceitos de Programação Orientada a Objetos, Java, JDBC, banco de dados e organização de código, que haviam sido estudados anteriormente.

## Contexto acadêmico

Projeto desenvolvido para uma avaliação acadêmica da disciplina de Programação Orientada a Objetos.
