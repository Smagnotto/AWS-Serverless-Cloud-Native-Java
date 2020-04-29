package br.com.fiap.trips.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = { "id", "country", "city", "date", "url_bucket"})
public class TripCreatedDTO {

    public TripCreatedDTO( String id, TripCreateDTO createDTO, String urlBucket){
        this.id = id;
        this.country = createDTO.getCountry();
        this.city = createDTO.getCity();
        this.urlBucket = urlBucket;
        this.date = createDTO.getDate();
    }


    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("url_bucket")
    private String urlBucket;

    @JsonProperty("id")
    private String id;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public String getUrlBucket() {
        return urlBucket;
    }

    public void setUrlBucket(String urlBucket) {
        this.urlBucket = urlBucket;
    }
}