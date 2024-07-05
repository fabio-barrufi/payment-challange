# 🏦 Projeto de Transações Bancárias

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.7-brightgreen)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5.Final-blue)
![MongoDB](https://img.shields.io/badge/MongoDB--green)
![JUnit](https://img.shields.io/badge/JUnit--blue)
![Docker](https://img.shields.io/badge/Docker--blue)

## 📝 Sumário

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Endpoints Disponíveis](#endpoints-disponíveis)
- [Configuração do Cronjob](#configuração-do-cronjob)
- [Como Executar](#como-executar)
- [Contribuições](#contribuições)
- [Licença](#licença)

## 📖 Descrição

Este projeto é uma aplicação de transações bancárias, que permite realizar operações como criação de transações, consulta de extratos e outras operações financeiras. A aplicação foi desenvolvida utilizando Java com Spring Boot, seguindo a Clean Architecture, e utilizando várias outras tecnologias para garantir robustez, escalabilidade e facilidade de manutenção.

## 🚀 Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada.
- **Spring Boot 3.2.7**: Framework para construção de aplicações Java.
- **MapStruct**: Framework para mapeamento de objetos.
- **MongoDB**: Banco de dados NoSQL.
- **JUnit**: Framework de testes.
- **Docker**: Plataforma para criação e gerenciamento de containers.

## 🏛️ Arquitetura

O projeto segue os princípios da Clean Architecture, dividindo a aplicação em camadas que facilitam a manutenção e evolução do código:

- **Entrypoint**: Responsável pela entrada das requisições HTTP no serviço, é nela onde ficam minhas controllers. Não contém regras de negócio, mas pode ser feita a validação de campos obrigatórios vindas na requisição, por exemplo.
- **Use Cases**: Responsável pela lógica de negócios e aplicação. Contém as interfaces e implementações dos casos de uso.
- **Gateways**: Responsável pela comunicação com fontes externas de dados, como bancos de dados ou serviços externos. Implementa as interfaces definidas na camada de boundary/use case.
- **Camada de Domínio**: Contém as entidades de domínio da aplicação, que representam os conceitos centrais e as regras de negócio da aplicação. As entidades podem conter métodos que representam operações relacionadas a elas, mas a lógica de negócio mais complexa deve estar nos casos de uso.
- **Camada de Mapeamento**: Responsável pela conversão de objetos entre diferentes camadas.

#### Criar Transação

- **URL**: `POST /transacoes`
- **Descrição**: Cria uma nova transação.
- **Corpo da Requisição**:
  ```json
  {
    "codigoMerchant": "3f5a45b2-135a-4660-aef1-0f0bbe5ea8df",
    "valor": 100,
    "descricao": "Mercearia do Johnson",
    "metodoPagamento": "credit",
    "numeroCartao": "1234567891011212",
    "nomePortadorCartao": "JOSUE B MIRANDA",
    "dataValidadeCartao": "01/29",
    "cvvCartao": "120"
  }
  ```

#### Obter Transação por ID

- **URL**: `GET /transacoes/:id`
- **Descrição**: Obtém uma transação pelo seu ID.
- **Parâmetros**:
  - `id`: ID da transação

#### Obter Todas Transações

- **URL**: `GET /transacoes`
- **Descrição**: Obtém todas as transações.
- **Parâmetros de Consulta (Query Parameters)**:
  - `descricao` (opcional): Descrição da transação
  - `metodoPagamento` (opcional): Método de pagamento
  - `nomePortadorCartao` (opcional): Nome do portador do cartão

#### Saldo

#### Obter Saldo Disponível

- **URL**: `GET /saldo/disponivel`
- **Descrição**: Obtém o saldo disponível.
- **Parâmetros de Consulta (Query Parameters)**:
  - `codigoMerchant`: Código do Merchant

#### Obter Saldo Aguardando Fundos

- **URL**: `GET /saldo/aguardando-fundos`
- **Descrição**: Obtém o saldo que está aguardando fundos.
- **Parâmetros de Consulta (Query Parameters)**:
  - `codigoMerchant`: Código do Merchant

## ⏲️ Configuração do Cronjob

A aplicação possui um cronjob configurado para alterar o status do pagamento para "paid" assim que a data de pagamento for menor que a data atual e o status estiver como "waiting_funds". Atualmente ele está configurado para rodar de minuto em minuto.

```java
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AtualizaPagamentoCronJob {

    @Scheduled(cron = "0 * * * * ?")
    public void atualizarStatusTransacoes() {
        ...
    }
}
```

## 🛠️ Como Executar

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/fabio-barrufi/payment-challange.git
   ```

2. **Navegue até o diretóirio**:
   ```bash
   cd payment-challange
   ```
3. **Execute o Docker Compose para iniciar os serviços**:

   ```bash
   docker-compose up
   ```

4. **Execute a aplicação:**

   ```bash
   ./mvnw spring-boot:run
   ```

**A aplicação estará disponível em http://localhost:8080.**

**E swagger disponível em: http://localhost:8080/swagger-ui/index.html**
