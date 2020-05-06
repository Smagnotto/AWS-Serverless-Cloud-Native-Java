package br.com.fiap.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import br.com.fiap.dto.TripDTO;
import br.com.fiap.model.Trip;
import br.com.fiap.util.RandomUtil;

public class TripRepository {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	public Trip save(TripDTO tripDTO) {
		Trip trip = new Trip(tripDTO);

		String bucketName = getBucketName(trip);
		String s3Key = String.format(UUID.randomUUID().toString().replace("-", ""), bucketName);
		
		trip.setUrl(mapper.createS3Link(bucketName, s3Key));

		mapper.save(trip);
		return trip;
	}

	public List<Trip> findByPeriod(final String start, final String end) {

		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#d", "Date");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":start", new AttributeValue().withS(start));
		expressionAttributeValues.put(":end", new AttributeValue().withS(end));

		DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
		.withIndexName("DateIndex")
				.withConsistentRead(false)
				.withKeyConditionExpression("#d between :start and :end")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues);

		List<Trip> lstTrips = mapper.query(Trip.class, queryExpression);

		return lstTrips;
	}

	public Trip findById(final String id) {
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":id", new AttributeValue().withS(id));

		DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
				.withKeyConditionExpression("Id = :id").withExpressionAttributeValues(expressionAttributeValues);

		List<Trip> lstTrips = mapper.query(Trip.class, queryExpression);

		if (lstTrips.size() > 0)
			return lstTrips.get(0);
		else
			return null;
	}

	private String getBucketName(Trip trip) {
		BigInteger randomNumer = new BigInteger(RandomUtil.getRandomNumber(10));
		String name = String.format("%s-%s-%s-%s", trip.getCountry(), trip.getCity(), trip.getDate(), randomNumer);

		name = name.replace(" ", "").toLowerCase();

		return name;
	}
}