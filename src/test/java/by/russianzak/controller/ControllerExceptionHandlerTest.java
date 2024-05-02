package by.russianzak.controller;

import by.russianzak.controller.handlers.ControllerExceptionHandler;
import by.russianzak.exceptions.WebError;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerExceptionHandlerTest {

  private final ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();

  @Test
  void handleResponseStatusException() {
    ResponseStatusException e = new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleResponseStatusException(e);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Resource not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
  }

  @Test
  void handleHttpMessageNotReadableException() {
    HttpMessageNotReadableException e = new HttpMessageNotReadableException("Could not parse json");
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleHttpMessageNotReadableException(e);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Could not parse json", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatus());
  }

  @Test
  void handleNoHandlerFoundException() {
    NoHandlerFoundException e = new NoHandlerFoundException("GET", "/path", null);
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleNoHandlerFoundException(e);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("No endpoint GET /path.", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
  }

  @Test
  void handleUnknownHttpStatusCodeException() {
    UnknownHttpStatusCodeException e = new UnknownHttpStatusCodeException(HttpStatus.NOT_FOUND.value(), "status text", null, null, null);
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleUnknownHttpStatusCodeException(e);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Unknown status code [404] status text", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
  }

  @Test
  void handleHttpClientErrorException() {
    HttpClientErrorException e = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Resource not found");
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleHttpClientErrorException(e);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("404 Resource not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
  }

  @Test
  void handleIllegalArgumentException() {
    IllegalArgumentException e = new IllegalArgumentException("Invalid argument");
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleIllegalArgumentException(e);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Invalid argument", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatus());
  }

  @Test
  void handleHttpServerErrorException() {
    HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    ResponseEntity<WebError> responseEntity = controllerExceptionHandler.handleHttpServerErrorException(ex);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    assertEquals("500 Internal Server Error", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
  }
}
