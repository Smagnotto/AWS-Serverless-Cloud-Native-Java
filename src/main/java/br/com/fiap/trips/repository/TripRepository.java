package br.com.fiap.trips.repository;

import org.springframework.stereotype.Component;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.dto.TripCreatedDTO;

@Component
public interface TripRepository {
    TripCreatedDTO save(TripCreateDTO trip);
}