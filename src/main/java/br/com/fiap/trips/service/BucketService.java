package br.com.fiap.trips.service;

import java.math.BigInteger;
import java.net.URL;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.util.RandomUtil;

@Service
public class BucketService {


    public String createBucketWithUrl(TripCreateDTO createDTO) {
        String bucketName = getBucketName(createDTO);

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();

            if (!s3Client.doesBucketExistV2(bucketName)) {
                //Cria o bucket
                s3Client.createBucket(new CreateBucketRequest(bucketName));

                // Verifica se o bucket foi criado, pegando a localidade
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                if (bucketLocation.isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível encontrar o bucket " + bucketName);
                
                    //TODO caso de erro abaixo, deletar bucket e estourar exception
                return getUrlBucket(bucketName);
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

    private String getUrlBucket(String bucketName) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, "photo-" + UUID.randomUUID().toString())
                            .withMethod(HttpMethod.GET);

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
            //TODO Estourar exception
            
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            //TODO Estourar exception
        }

        return null;
    }

}