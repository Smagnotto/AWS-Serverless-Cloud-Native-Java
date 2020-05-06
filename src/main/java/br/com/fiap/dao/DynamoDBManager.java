package br.com.fiap.dao;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBManager {

	private static DynamoDBMapper mapper;
	private static AmazonDynamoDB ddb;

	static {
		final String endpoint = System.getenv("ENDPOINT_OVERRIDE");
		
        if (endpoint != null && !endpoint.isEmpty()) {
        	EndpointConfiguration endpointConfiguration = new EndpointConfiguration(endpoint, "us-east-1");
        	ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).build();
        } else {
        	ddb = AmazonDynamoDBClientBuilder.defaultClient();
        }

		mapper = new DynamoDBMapper(ddb);
	}

	public static DynamoDBMapper mapper() {
		return DynamoDBManager.mapper;
	}

	public static AmazonDynamoDB amazonDynamo(){
		return DynamoDBManager.ddb;
	}

	public static DynamoDB dynamoDb() {
		return new DynamoDB(DynamoDBManager.ddb);
	}
}