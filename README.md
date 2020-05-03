# Trips API

### Tecnologias

  - Java SE 8
  - Spring Boot
  - Amazon DynamoDB
  - Amazon Bucket S3
  - Amazon Lambda

### Configuração do Ambiente
Antes começar precisamos configurar o ambiente. Precisamos configurar as credencias da AWS. Veja a seguir um exemplo de um arquivo de credenciais da AWS chamado ~/.aws/credentials (%USERPROFILE%/.aws/credentials no Windows).

```
[default]
aws_access_key_id={YOUR_ACCESS_KEY_ID}
aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}
```

No console da AWS, é necessário acessar o `DynamoDB` e criar tabela com o nome `Trip` e defina uma chave com o nome `Id` e o tipo `String`.

### Execução
Para executar o projeto localmente, é necessário a instalação do [Maven](https://maven.apache.org/download.cgi). 

Efetuar o clone do repositório git na pasta em que desejar. Depois de efetuar o clone do projeto, executar os comandos abaixo no terminal (cmd no Windows) para compilar o projeto.

```sh
cd [Pasta do projeto]
mvn clean install
```

Com os comandos acima, será criado um .JAR do projeto, dentro da pasta. Executar os comandos abaixo para executar o projeto:

```sh
cd [PASTA PROJETO]\target -- confirmar essa 
[colocar comando para executar o projeto]
```

Dentro do repositório clonado, existe uma pasta chamada `postman-collection`, com uma collection do [Postman]() para execução das rotas da API.

#### Documentação

Na API existe três rotas disponíveis, sendo elas:
> POST [base_url]/trip

Essa rota é reponsável por incluir uma nova viagem, essa viagem será salvo no `DynamoDB` e será criado um `Bucket S3` para upload das fotos.

* Payload de entrada:

```json
{ 
	"country": "Brazil",
	"city": "Sao Paulo",
	"date": "2020-05-01"
}
```

* Payload de saída:



