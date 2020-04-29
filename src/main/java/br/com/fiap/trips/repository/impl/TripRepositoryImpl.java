package br.com.fiap.trips.repository.impl;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.repository.TripRepository;
import br.com.fiap.trips.service.BucketService;

@Repository
public class TripRepositoryImpl implements TripRepository {

    @Autowired
    private BucketService bucketService;

    @Override
    public TripCreatedDTO save(TripCreateDTO trip) {


         AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
         .withCredentials(new ProfileCredentialsProvider())
         .withRegion(Regions.DEFAULT_REGION)
         .build();
         
         DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("trip");
        try {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String id = UUID.randomUUID().toString();
            String urlBucket = bucketService.createBucket(trip);

            Item item = new Item().withPrimaryKey("id",id )
                .withString("country", trip.getCountry())
                .withString("city", trip.getCity())
                .withString("date", formatter.format(trip.getDate()))
                .withString("url_bucket", urlBucket);

            table.putItem(item);

            return new TripCreatedDTO(id, trip, urlBucket);

        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao salvar a trip");
        }
    }

}