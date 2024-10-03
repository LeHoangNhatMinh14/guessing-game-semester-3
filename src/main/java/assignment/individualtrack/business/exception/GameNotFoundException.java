package assignment.individualtrack.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameNotFoundException extends ResponseStatusException {
  public GameNotFoundException(String errorCode) {
    super(HttpStatus.NOT_FOUND, errorCode);
  }
}
