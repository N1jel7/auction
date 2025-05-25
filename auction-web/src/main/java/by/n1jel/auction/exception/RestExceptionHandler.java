package by.n1jel.auction.exception;

import by.n1jel.auction.dto.ErrorResponseDto;
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
    public ResponseEntity<ErrorResponseDto> badCredentials(BadCredentialsException ex){
        return ResponseEntity.status(401)
                .body(
                        new ErrorResponseDto(401, ex.getMessage(), "Wrong password or username")
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleArgumentNotValid(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().
                stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.status(406).body(
                new ErrorResponseDto(406, "Validation failed", errors.toString()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(404)
                .body(
                        new ErrorResponseDto(404, ex.getMessage(), ex.getLocalizedMessage())
                );
    }


    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleLotNotFound(LotNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(
                        new ErrorResponseDto(404, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(405)
                .body(
                        new ErrorResponseDto(405, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(404)
                .body(
                        new ErrorResponseDto(404, ex.getMessage(), ex.getLocalizedMessage())
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(406)
                .body(
                        new ErrorResponseDto(406, ex.getMessage(), ex.getLocalizedMessage())
                );
    }


}
