package br.com.fiap.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;

public class GetTripRecordsByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();
	
	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String starts = request.getQueryStringParameters().get("start");
		final String ends = request.getQueryStringParameters().get("end");

		context.getLogger().log("Procurando por viagens no periodo de " + starts + " e " + ends);

		try{
			final List<Trip> trips = this.repository.findByPeriod(starts, ends);
		
			return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
		}
		catch (Exception ex) {
			context.getLogger().log(ex.getMessage());
			throw ex;
		}
	}
}