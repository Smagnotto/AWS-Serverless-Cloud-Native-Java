package br.com.fiap.trips.service;

import java.math.BigInteger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.util.RandomUtil;

@Service
public class BucketService {

    @Value("{cloud.aws.credentials.accessKey}")
    private String id;

    @Value("{cloud.aws.credentials.secretKey}")
    private String secretKey;

    public String createBucket(TripCreateDTO createDTO) {
        String bucketName = getBucketName(createDTO);

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(Regions.DEFAULT_REGION)
                .build();

            if (!s3Client.doesBucketExistV2(bucketName)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                s3Client.createBucket(new CreateBucketRequest(bucketName));

                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                if (bucketLocation.isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível encontrar o bucket " + bucketName);

                return bucketName;
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it and returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

        return "";
    } 

    private String getBucketName(TripCreateDTO createDTO) {
        BigInteger randomNumer = new BigInteger(RandomUtil.getRandomNumber(10));
        String name = String.format("%s-%s-%s-%s", createDTO.getCountry(), createDTO.getCity(), createDTO.getDate(), randomNumer);

        name = name.replace(" ", "").toLowerCase();

        return name;
    }

}