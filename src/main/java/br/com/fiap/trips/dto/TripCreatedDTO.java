package br.com.fiap.trips.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = { "id", "url_bucket"})
public class TripCreatedDTO {

    public TripCreatedDTO( String id, String urlBucket){
        this.id = id;
        this.urlBucket = urlBucket;
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("url_bucket")
    private String urlBucket;

    public String getUrlBucket() {
        return urlBucket;
    }

    public void setUrlBucket(String urlBucket) {
        this.urlBucket = urlBucket;
    }
}