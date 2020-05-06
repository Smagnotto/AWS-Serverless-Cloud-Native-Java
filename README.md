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

## Packaging and deployment
Primeiramente, nós precisamos de um `bucket S3` onde podemos realizar o upload das nossas funções lambadas. Para criar um bucket, execute os comandos abaixo:

```bash
export BUCKET_NAME=my_cool_new_bucket
aws s3 mb s3://$BUCKET_NAME
```
Vamos rodar agora o comando para empacotar nossas funções Lambas no S3:
```bash
sam package \
    --template-file template.yaml \
    --output-template-file packaged.yaml \
    --s3-bucket $BUCKET_NAME
```

O próximo comando irá criar a Cloudformation Stack e realizar o deploy dos resources SAM.

```bash
sam deploy \
    --template-file packaged.yaml \
    --stack-name trip \
    --capabilities CAPABILITY_IAM
```

> **Veja [Serverless Application Model (SAM) HOWTO Guide](https://github.com/awslabs/serverless-application-model/blob/master/HOWTO.md) para mais detalhes.**

Depois que o deploy esteja completo, você consegue executar o comando abaixo pare pegar a URL do API Gateway:

```bash
aws cloudformation describe-stacks \
    --stack-name sam-orderHandler \
    --query 'Stacks[].Outputs'
```

## Integrantes:

| Nome | RM |
| ---- | -- |
| Bruno Gea | RM 333475 |
| Diego Soares Smagnotto Saraiva | RM 333886 |
| Eduardo Matoso | RM 333906 |
| Thiago Langoni | RM 333947 |
| Felipe da Costa | RM 333862 |