package br.com.fiap.trips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.service.BucketService;
import br.com.fiap.trips.service.TripService;
import br.com.fiap.trips.dto.TripCreateDTO;

@RestController
@RequestMapping("trip")
public class TripController {

    @Autowired
    private TripService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TripCreatedDTO createTrip(@RequestBody TripCreateDTO trip) {
        return service.createTrip(trip);
    }
}