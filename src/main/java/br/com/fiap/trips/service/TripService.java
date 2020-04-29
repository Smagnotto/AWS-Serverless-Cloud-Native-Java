package br.com.fiap.trips.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.repository.TripRepository;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    public TripCreatedDTO createTrip(TripCreateDTO trip) {
        return repository.save(trip);
    }
}