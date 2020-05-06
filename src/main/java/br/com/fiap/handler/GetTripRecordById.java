package br.com.fiap.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.dto.CreatedTripDTO;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;

public class GetTripRecordById implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String id = request.getPathParameters().get("id").trim();

		context.getLogger().log("Procurando por viagem: " + id);

		try {
			final Trip trip = this.repository.findById(id);

			if (trip == null)
				return HandlerResponse.builder().setStatusCode(404).build();

			return HandlerResponse.builder().setStatusCode(200).setObjectBody(new CreatedTripDTO(trip)).build();
		} catch (Exception ex) {
			context.getLogger().log(ex.getMessage());
			throw ex;
		}
	}
}