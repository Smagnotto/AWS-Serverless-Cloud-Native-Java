package br.com.fiap.trips.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TripNotFoundException extends ResponseStatusException {
    public TripNotFoundException(HttpStatus status) {
        super(status);
    }

    private static final long serialVersionUID = 1L;
 }