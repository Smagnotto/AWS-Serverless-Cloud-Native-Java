package br.com.fiap.dto;

public class TripDTO {
    private String city;
    private String country;
    private String date;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TripDTO(String city, String country, String date) {
        this.city = city;
        this.country = country;
        this.date = date;
    }

    public TripDTO() { }
}