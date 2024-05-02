package by.russianzak.controller.handlers;

import by.russianzak.exceptions.WebError;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<WebError> handleResponseStatusException(ResponseStatusException e) {
    return new ResponseEntity<>(new WebError(e.getStatusCode().value(), e.getReason()),
        e.getStatusCode());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<WebError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<WebError> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return new ResponseEntity<>(new WebError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<WebError> handleHttpServerErrorException(HttpServerErrorException ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getMessage()), ex.getStatusCode());
  }

  @ExceptionHandler(UnknownHttpStatusCodeException.class)
  public ResponseEntity<WebError> handleUnknownHttpStatusCodeException(UnknownHttpStatusCodeException ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<WebError> handleHttpClientErrorException(HttpClientErrorException ex) {
    return new ResponseEntity<>(new WebError(ex.getRawStatusCode(), ex.getMessage()), ex.getStatusCode());
  }

  @ExceptionHandler({IllegalArgumentException.class, PropertyValueException.class})
  public ResponseEntity<WebError> handleIllegalArgumentException(IllegalArgumentException e) {
    return new ResponseEntity<>(new WebError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }
}

