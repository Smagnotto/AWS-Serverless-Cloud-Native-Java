package br.com.fiap.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import br.com.fiap.model.Trip;

public class TripRepository {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	public Trip save(final Trip Trip) {
		mapper.save(Trip);
		return Trip;
	}

	public List<Trip> findByPeriod(final String starts, final String ends) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(starts));
		eav.put(":val2", new AttributeValue().withS(ends));

		Map<String,String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#date_trip","date");

		final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
				.withExpressionAttributeNames(expressionAttributesNames)
				.withKeyConditionExpression("#date_trip between :val1 and :val2")
				.withExpressionAttributeValues(eav);

		final List<Trip> studies = mapper.query(Trip.class, queryExpression);

		return studies;
	}
}