package br.com.fiap.trips.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.fiap.trips.dto.TripCreateDTO;
import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.dto.TripGetDTO;

@Component
public interface TripRepository {
    TripCreatedDTO save(TripCreateDTO trip);
    List<TripGetDTO> getByPeriod(LocalDate start, LocalDate end);
    TripGetDTO getById(String id);
}