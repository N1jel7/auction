package by.n1jel.auction.exception;

import by.n1jel.auction.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValid(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().
                stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.status(406).body(ErrorResponse.builder()
                .status(406)
                .error("Validation failed")
                .description(errors.toString())
                .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(404)
                .body(
                        ErrorResponse.builder()
                                .status(404)
                                .error(ex.getMessage())
                                .description(ex.getLocalizedMessage())
                                .build()
                );
    }


    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLotNotFound(LotNotFoundException ex) {

        return ResponseEntity.status(404)
                .body(
                        ErrorResponse.builder()
                        .status(404)
                        .error(ex.getMessage())
                        .description(ex.getLocalizedMessage())
                        .build()
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity.status(405)
                .body(
                        ErrorResponse.builder()
                                .status(405)
                                .error(ex.getMessage())
                                .description(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentMismatch(MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.status(404)
                .body(
                        ErrorResponse.builder()
                                .status(404)
                                .error(ex.getMessage())
                                .description(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {

        return ResponseEntity.status(406)
                .body(
                        ErrorResponse.builder()
                                .status(406)
                                .error(ex.getMessage())
                                .description(ex.getLocalizedMessage())
                                .build()
                );
    }


}
