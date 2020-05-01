package br.com.fiap.trips.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fiap.trips.model.TripEntity;

public class TripGetDTO {
    public TripGetDTO(TripEntity entity) {
        this.id = entity.getId();
        this.urlBucket = entity.getUrl();
        this.date = entity.getDate();
        this.country = entity.getCountry();
        this.city = entity.getCity();
    }


    @JsonProperty("id")
    private String id;

    @JsonProperty("url_bucket")
    private String urlBucket;

    @JsonProperty("date")
    private String date;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlBucket() {
        return urlBucket;
    }

    public void setUrlBucket(String urlBucket) {
        this.urlBucket = urlBucket;
    }
}