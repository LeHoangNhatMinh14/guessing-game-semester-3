package assignment.individualtrack.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPlayerException extends ResponseStatusException {
    public InvalidPlayerException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
