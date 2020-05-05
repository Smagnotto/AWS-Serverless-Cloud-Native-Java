# Trips API

### Requerimentos
* [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html)
* [Java SE Development Kit 8 installed](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Docker installed](https://www.docker.com/community-edition)
* [Maven](https://maven.apache.org/install.html)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)

## Configuração do Ambiente
Antes começar precisamos configurar o ambiente. Precisamos configurar as credencias da AWS. Veja a seguir um exemplo de um arquivo de credenciais da AWS chamado ~/.aws/credentials (%USERPROFILE%/.aws/credentials no Windows).

```
[default]
aws_access_key_id={YOUR_ACCESS_KEY_ID}
aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}
```

> Para mais informações, consulte [Configure the AWS Cli](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html)

## Processo de Setup

### Instalando as dependências
Vamos utilizar o `maven` para instalar nossas dependências e empacotar nossa aplicação dentro de um arquivo JAR:

```bash
mvn install
```

### Execução local

**Executando a aplicação localmente através de um API Gateway local**
1. Execute o `DynamoDB` localmanete dentro do docker, utilizando o seguinte comando `docker run -p 8000:8000 amazon/dynamodb-local`
2. Crie a tabela no `DynamoDB`, utilizando o seguinte comando `aws dynamodb create-table --cli-input-json file://table-trip.json --endpoint-url http://localhost:8000`

Caso a tabela já exista, você pode excluir utilizando o seguinte comando: `aws dynamodb delete-table --table-name trip --endpoint-url http://localhost:8000`

3. Inicie o AWS SAM localmente: 
* No MacOS: `sam local start-api --env-vars src/test/resources/test_environmen`
* No Windows: `sam local start-api --env-vars src/test/resources/test_environment_windows.json`
* No Linux: `sam local start-api --env-vars src/test/resources/test_environment_linux.json`

Se o comando anterior rodar com sucesso, você deverá conseguir bater no seguinte endpoint localmente para invocar as funções configuradas. Você pode explorar todos os endpoints, importe a collection AWS API Trip.postman_collection.json no Postman.
