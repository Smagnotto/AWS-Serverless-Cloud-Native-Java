package br.com.fiap.dto;

import br.com.fiap.model.Trip;

public class CreatedTripDTO extends TripDTO {
    private String id;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CreatedTripDTO(Trip trip) {
        super(trip.getCity(), trip.getCountry(), trip.getDate());
        this.id = trip.getId();
        this.url = trip.getUrl().getUrl().toString();
    }
}