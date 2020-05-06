package br.com.fiap.dao;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

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

		DefaultAWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();

		mapper = new DynamoDBMapper(ddb, credentials);
	}

	public static DynamoDBMapper mapper() {
		return DynamoDBManager.mapper;
	}
}