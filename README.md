# finance-api

Uma API em Java e Spring Framework para gerenciamento de transações financeiras.

## Como a API deve funcionar?

Nossa API deve criar, atualizar, deletar e listar transações financeiras. Além disso, deve calcular estatísticas sobre as transações criadas. A API terá os seguintes endpoints:

`POST/transaction`: cria uma transação. 

**Body:**

<code>
{
  "id": 1,
  "nsu": "220788",
  "autorizationNumber": "010203",
  "amount": "22.88",
  "transactionDate": "2019-09-11T09:59:51.312Z",
  "type": "CARD"
}
</code>

**Where:**

`id`: número único da transação;
`nsu`: número de identificação de uma transação de cartão de crédito. Pode ser nula se a transação for paga em dinheiro.
`autorizationNumber`: é código único das transações online.
`amount`: valor da transação; deve ser uma String de tamanho arbitrário que pode ser parseada como um BigDecimal;
`transactionDate`: data da transação no formato ISO 8601 YYYY-MM-DDThh:mm:ss.sssZ no timezone local.
`type`: se a transação é em cartão (CARD) ou em dinheiro (MONEY).

Deve retornar com body vazio com um dos códigos a seguir:

* 201: em caso de sucesso.
* 400: caso o JSON seja inválido.
* 422: se qualquer um dos campos não for parseável ou a data da transação está no futuro.

`PUT/transaction/{id}`: atualiza uma transação.

**Body:**

<code>
{
  "id": 1,
  "nsu": "220788",
  "autorizationNumber": "010203",
  "amount": "30.06",
  "transactionDate": "2019-09-11T09:59:51.312Z",
  "type": "CARD"
}
</code>

Deve ser enviado o objeto que será modificado. O retorno deve ser o próprio objeto modificado.

<code>
{
  "id": 1,
  "nsu": "220788",
  "autorizationNumber": "010203",
  "amount": "30.06",
  "transactionDate": "2019-09-11T09:59:51.312Z",
  "type": "CARD"
}
</code>

A resposta deve conter os códigos a seguir:

* 200: em caso de sucesso.
* 400: caso o JSON seja inválido.
* 404: caso tentem atualizar um registro que não existe.
* 422: se qualquer um dos campos não for parseável (JSON mal formatado).

`GET/transactions`: retorna todas as transações criadas.

Deve retornar uma lista de transações.

<code>
{   
   "id": 1,
   "nsu": "220788",
   "autorizationNumber": "010203",
   "amount": "30.06",
   "transactionDate": "2019–09–11T09:59:51.312Z",
   "type": "CARD"
},
{   
   "id": 2,
   "nsu": "300691",
   "autorizationNumber": "040506",
   "amount": "120.0",
   "transactionDate": "2019–09–11T10:22:30.312Z",
   "type": "CARD"
}
</code>

A resposta deve conter os códigos a seguir:

* 200: caso exista transações cadastradas
* 404: caso não exista transações criadas.

`DELETE/transactions`: remove todas as transações.

Deve aceitar uma requisição com body vazio e retornar 204.

`GET/statistics`: retorna estatísticas básicas sobre as transações criadas.

<code>
{   
   "sum": "150.06",
   "avg": "75.3",
   "max": "120.0",
   "min": "30.06",
   "count": "2"
}
</code>

Em que:
`sum`: um BigDecimal especificando a soma total das transações criadas.
`avg`: um BigDecimal especificando a média dos valores das transações criadas.
`max`: um BigDecimal especificando o maior valor dentre as transações criadas.
`min`: um BigDecimal especificando o menor valor dentre as transações criadas.
`count`: um long especificando o número total de transações.

Todos os campos que são BigDecimal devem ter apenas duas casas decimais, por exemplo: 15.385 deve ser retornado como 15.39. 

### Testes

* Para executar o teste unitário, o comando executado deve ser:

```
mvn test
```

* Para executar todos os testes (incluindo o de integração), o comando executado deve ser:

```
mvn integration-test
```

### Execução

Para rodar a API via .jar:

```
java -jar finance-api-1.0.1.jar --spring.profiles.active=dev
```
    
ou

```
mvn spring-boot:run -Dspring.profiles.active=dev
```

Por default, a API está disponível no endereço [http://localhost:8080/](http://localhost:8080/)
