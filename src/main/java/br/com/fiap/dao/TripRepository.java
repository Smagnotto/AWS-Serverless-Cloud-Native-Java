package br.com.fiap.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import br.com.fiap.model.Trip;

public class TripRepository {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();
	private static final DynamoDB dynamoDB = DynamoDBManager.dynamoDb();

	public Trip save(final Trip Trip) {
		mapper.save(Trip);
		return Trip;
	}

	public List<Trip> findByPeriod(final String start, final String end) {

		Table table = dynamoDB.getTable("trip");
		Index index = table.getIndex("DateIndex");

		QuerySpec spec = new QuerySpec()
				.withKeyConditionExpression("#d between :start and :end")
				.withNameMap(new NameMap().with("#d", "Date"))
				.withValueMap(new ValueMap()
								.withString(":start", start)
								.withString(":end", end));

		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator();

		while (iter.hasNext()) {
            System.out.println(iter.next().toJSONPretty());
        }

		return null;
	}

	public Trip findById(final String id) {
		return mapper.load(Trip.class, id);
	}
}