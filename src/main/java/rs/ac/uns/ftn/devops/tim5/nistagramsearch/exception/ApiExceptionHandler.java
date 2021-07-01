package rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.ErrorMessageDTO;

import java.time.ZonedDateTime;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class, CanNotAccessException.class})
    public ResponseEntity<ErrorMessageDTO> handleException(Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(e.getMessage(), badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(errorMessageDTO, badRequest);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessageDTO> handleBadRequest(MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(message, badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(errorMessageDTO, badRequest);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessageDTO> handleNotFound(ResourceNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(e.getMessage(), notFound, ZonedDateTime.now());
        return new ResponseEntity<>(errorMessageDTO, notFound);
    }
}
