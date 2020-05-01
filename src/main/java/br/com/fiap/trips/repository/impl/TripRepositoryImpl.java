package br.com.fiap.trips.repository.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.trips.config.TripNotFoundException;
import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.dto.TripGetDTO;
import br.com.fiap.trips.model.TripEntity;
import br.com.fiap.trips.repository.TripRepository;
import br.com.fiap.trips.service.BucketService;

@Repository
public class TripRepositoryImpl implements TripRepository {

    @Autowired
    private BucketService bucketService;

    final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    @Override
    public TripCreatedDTO save(TripCreateDTO trip) {

        try {

            DynamoDBMapper mapper = new DynamoDBMapper(client);

            // TODO Caso de erro, excluir item.
            String urlBucket = bucketService.createBucketWithUrl(trip);

            TripEntity item = new TripEntity();
            item.setCountry(trip.getCity());
            item.setCity(trip.getCity());
            item.setUrl(urlBucket);
            item.setDate(trip.getDate().toString());

            mapper.save(item);

            return new TripCreatedDTO(item.getId(), urlBucket);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao salvar a trip");
        }
    }

    public List<TripGetDTO> getByPeriod(LocalDate start, LocalDate end) {

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":start", new AttributeValue().withS(start.toString()));
        eav.put(":end", new AttributeValue().withS(end.toString()));

        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withFilterExpression("TripDate BETWEEN :start and :end").withExpressionAttributeValues(eav);

        List<TripEntity> allTrips = mapper.scan(TripEntity.class, queryExpression);

        return allTrips.stream().map(TripGetDTO::new).collect(Collectors.toList());
    }

    public TripGetDTO getById(String hashId) {

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        TripEntity trip = mapper.load(TripEntity.class, hashId);

        if (trip == null)
            throw new TripNotFoundException(HttpStatus.NOT_FOUND);

        return new TripGetDTO(trip);
    }
}