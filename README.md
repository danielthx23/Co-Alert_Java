# Co-Alert

## Descrição do Projeto

O Co-Alert surge como uma rede colaborativa de comunicação, que funciona como uma plataforma tipo fórum, permitindo aos usuários compartilhar informações em tempo real sobre desastres naturais e eventos climáticos extremos. Através de publicações detalhadas, que incluem tipo de ocorrência, localização exata e imagens, a comunidade pode acessar informações confiáveis e atualizadas, melhorando a tomada de decisões e a preparação para esses eventos.

Nosso público-alvo é composto por pessoas que vivem em áreas vulneráveis a desastres naturais, indivíduos com acesso limitado a informações oficiais e qualquer pessoa interessada em estar preparada para situações de emergência climática.

Com o Co-Alert, buscamos impactar positivamente a vida dessas pessoas, reduzindo a desinformação e os alarmes falsos, facilitando o fluxo rápido e acessível de dados relevantes, para que possam agir com segurança e antecedência.

Este projeto responde a um desafio crescente: o aumento de até 460% nos desastres climáticos no Brasil desde os anos 1990, comprovando a necessidade de ferramentas eficientes para comunicação e prevenção

## Autores

### Turma 2TDSR - Análise e Desenvolvimento de Sistemas

* Daniel Saburo Akiyama - RM 558263
* Danilo Correia e Silva - RM 557540
* João Pedro Rodrigues da Costa - RM 558199

## ⚙️ Instalação do Projeto

### Requisitos

* Java SDK 21
* (Opcional) IntelliJ IDEA ou Eclipse

### Configuração

1. Edite o arquivo `src/main/resources/application.properties` com sua string de conexão Oracle:

```properties
spring.datasource.url=jdbc:oracle:thin:@${ORACLEHOST}:${ORACLEPORT}:${ORACLEDATABASE}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.username=${ORACLEUSER}
spring.datasource.password=${ORACLEPASSWORD}
```

### Rodar o Projeto

#### Opção 1: Usando Maven direto

```bash
./mvnw spring-boot:run
```

Ou, se já tiver Maven instalado:

```bash
mvn spring-boot:run
```

#### Opção 2: Usando IDE (Eclipse / IntelliJ)

Importe o projeto como Maven Project e execute a classe principal com `@SpringBootApplication`.

#### Opção 3: Compilar e rodar .jar

1. Gerar o `.jar`:

```bash
./mvnw clean package
```

Ou:

```bash
mvn clean package
```

2. Rodar o `.jar` com Java:

```bash
java -jar target/mottracker-0.0.1-SNAPSHOT.jar
```

### Acessar a API

* A API estará acessível em: [http://localhost:8080](http://localhost:8080)
* Documentação Swagger UI: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## Rotas da API

---

## Usuários

| Método | Endpoint               | Descrição                  |
|--------|------------------------|----------------------------|
| GET    | `/usuarios`            | Listar todos os usuários   |
| GET    | `/usuarios/{id}`       | Buscar usuário por ID      |
| POST   | `/usuarios`            | Criar novo usuário         |
| PUT    | `/usuarios/{id}`       | Atualizar usuário          |
| DELETE | `/usuarios/{id}`       | Excluir usuário            |

---

## Localizações

| Método | Endpoint                   | Descrição                     |
|--------|----------------------------|-------------------------------|
| GET    | `/localizacoes`            | Listar todas as localizações  |
| GET    | `/localizacoes/{id}`       | Buscar localização por ID     |
| POST   | `/localizacoes`            | Criar nova localização        |
| PUT    | `/localizacoes/{id}`       | Atualizar localização         |
| DELETE | `/localizacoes/{id}`       | Excluir localização           |

---

## Postagens

| Método | Endpoint              | Descrição                    |
|--------|-----------------------|------------------------------|
| GET    | `/postagens`          | Listar todas as postagens    |
| GET    | `/postagens/{id}`     | Buscar postagem por ID       |
| POST   | `/postagens`          | Criar nova postagem          |
| PUT    | `/postagens/{id}`     | Atualizar postagem           |
| DELETE | `/postagens/{id}`     | Excluir postagem             |

---

## Comentários

| Método | Endpoint               | Descrição                     |
|--------|------------------------|-------------------------------|
| GET    | `/comentarios`         | Listar todos os comentários   |
| GET    | `/comentarios/{id}`    | Buscar comentário por ID      |
| POST   | `/comentarios`         | Criar novo comentário         |
| PUT    | `/comentarios/{id}`    | Atualizar comentário          |
| DELETE | `/comentarios/{id}`    | Excluir comentário            |

---

## Categorias de Desastre

| Método | Endpoint                          | Descrição                            |
|--------|-----------------------------------|----------------------------------------|
| GET    | `/categorias-desastre`            | Listar todas as categorias             |
| GET    | `/categorias-desastre/{id}`       | Buscar categoria por ID                |
| POST   | `/categorias-desastre`            | Criar nova categoria de desastre       |
| PUT    | `/categorias-desastre/{id}`       | Atualizar categoria                    |
| DELETE | `/categorias-desastre/{id}`       | Excluir categoria                      |

---
