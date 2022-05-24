package account.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getFieldError().getDefaultMessage(),
                request.getDescription(false).replaceFirst("uri=",""));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    ex.getMessage(),
                    request.getDescription(false).replaceFirst("uri=",""));

            return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
        }


}

