package by.n1jel.auction.exception;

import by.n1jel.auction.config.JwtAuthenticationEntryPoint;
import by.n1jel.auction.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentials(BadCredentialsException ex){
        return ResponseEntity.status(401)
                .body(
                        new ErrorResponse(401, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValid(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().
                stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.status(406).body(
                new ErrorResponse(406, "Validation failed", errors.toString()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(404)
                .body(
                        new ErrorResponse(404, ex.getMessage(), ex.getLocalizedMessage())
                );
    }


    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLotNotFound(LotNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(
                        new ErrorResponse(404, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(405)
                .body(
                        new ErrorResponse(405, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(404)
                .body(
                        new ErrorResponse(404, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(406)
                .body(
                        new ErrorResponse(406, ex.getMessage(), ex.getLocalizedMessage())
                );
    }


}
