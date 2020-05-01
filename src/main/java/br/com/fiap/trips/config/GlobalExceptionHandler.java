package br.com.fiap.trips.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
 

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TripNotFoundException.class)
    public final ResponseEntity<TripNotFoundException> handleSpecificExceptions(final TripNotFoundException ex,
            final WebRequest request) {
      return new ResponseEntity<>(ex.getStatus());
    }
  }