package br.com.fiap.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "trip")
public class Trip {

	@DynamoDBHashKey(attributeName = "id")
	private String id;

	@DynamoDBIndexHashKey(attributeName = "date", globalSecondaryIndexName = "dateIndex")
	private String date;

	@DynamoDBAttribute(attributeName = "country")
	private String country;

	@DynamoDBAttribute(attributeName = "city")
	private String city;

	@DynamoDBAttribute(attributeName = "urlS3")
	private String url;

	public Trip(String id, String date, String country, String city, String url) {
		this.id = id;
		this.date = date;
		this.country = country;
		this.city = city;
		this.url = url;
	}

	public Trip() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Trip [city=" + city + ", country=" + country + ", date=" + date + ", id=" + id + ", url=" + url + "]";
	}
	
}
