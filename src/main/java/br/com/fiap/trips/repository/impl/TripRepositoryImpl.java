package br.com.fiap.trips.repository.impl;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.model.TripEntity;
import br.com.fiap.trips.repository.TripRepository;
import br.com.fiap.trips.service.BucketService;

@Repository
public class TripRepositoryImpl implements TripRepository {

    @Autowired
    private BucketService bucketService;

    @Override
    public TripCreatedDTO save(TripCreateDTO trip) {
   
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

            DynamoDBMapper mapper = new DynamoDBMapper(client);

            //TODO Caso de erro, excluir item.
            String urlBucket = bucketService.createBucketWithUrl(trip);

            TripEntity item = new TripEntity();
            item.setCountry(trip.getCity());
            item.setCity(trip.getCity());
            item.setUrl(urlBucket);

            mapper.save(item);

            return new TripCreatedDTO(item.getId(),urlBucket);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao salvar a trip");
        }
    }

}