package br.com.fiap.trips.controllers;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.trips.dto.TripCreatedDTO;
import br.com.fiap.trips.dto.TripGetDTO;
import br.com.fiap.trips.service.TripService;
import br.com.fiap.trips.dto.TripCreateDTO;

@RestController
@RequestMapping("trips")
public class TripController {

    @Autowired
    private TripService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TripCreatedDTO createTrip(@RequestBody TripCreateDTO trip) {
        return service.createTrip(trip);
    }

    @GetMapping()
    public List<TripGetDTO> getByPeriod(
            @RequestParam(name = "start", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return service.getByPeriod(start, end);
    }

    @GetMapping(path = "/{id}")
    public TripGetDTO getById(@PathVariable(name = "id", required = true) String id) {
        return service.getById(id);
    }

}