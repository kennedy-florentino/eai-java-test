# Address API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

## Descrição

Este é um serviço de cadastro de endereço. Ele fornece endpoints para registro de endereços utilizando o CEP fornecido pelo usuário para buscar informações em APIs externas (ViaCEP/OpenCEP).

## Tecnologias

As seguintes tecnologias foram utilizadas na construção do projeto:

- [Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
- [Spring Doc OpenAPI](https://springdoc.org/)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Lombok](https://projectlombok.org/)
- [MapStruct](https://mapstruct.org/)
- [Maven](https://jestjs.io/)

## Funcionalidades

- [x] Criação de endereço
- [x] Criação de endereços em lote
- [x] Busca de endereços cadastrados por CEP

## Pré-requisitos

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## Utilização

### Swagger

Com o projeto rodando acesse: http://localhost:8080/swagger-ui/index.html

Siga as intruções fornecidas pela interface do Swagger.

### Postman

No seu workspace do Postman, abra a aba Collections e clique em Import. Em seguida, selecione o arquivo `address-api.postman_collection.json` que está na pasta root do projeto.

Existem 3 endpoints na aplicação:

- **POST** - `/api/address`
- **POST** - `/api/address/batch`
- **GET** - `/api/address`

Na collection importada no Postman, haverá um modelo pronto para cada requisição.

## Testes

```bash
$ mvn test
```

## Autor

- Nome: Kennedy Florentino
- E-mail: kennedyf2k@gmail.com
- GitHub: [kennedy-florentino](https://github.com/kennedy-florentino)
- LinkedIn: [kennedyf2k](https://www.linkedin.com/in/kennedyf2k/)
