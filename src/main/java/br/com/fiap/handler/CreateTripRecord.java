package br.com.fiap.handler;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.dto.CreatedTripDTO;
import br.com.fiap.dto.TripDTO;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;

public class CreateTripRecord implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();

	@Override
	public HandlerResponse handleRequest(final HandlerRequest request, final Context context) {

		TripDTO trip = null;

		context.getLogger().log(request.getBody());

		try {
			trip = new ObjectMapper().readValue(request.getBody(), TripDTO.class);
		} catch (IOException e) {
			return HandlerResponse.builder().setStatusCode(400)
					.setRawBody("Existe um erro no objeto informado! " + e.getMessage()).build();
		}

		context.getLogger().log(
				String.format("Criando uma nova viagem para cidade %s, Pais %s", trip.getCity(), trip.getCountry()));
		try {
			final Trip tripRecorded = repository.save(trip);
			return HandlerResponse.builder().setStatusCode(201).setObjectBody(new CreatedTripDTO(tripRecorded)).build();
		} catch (Exception ex) {
			context.getLogger().log(ex.getMessage());
			throw ex;
		}
	}
}