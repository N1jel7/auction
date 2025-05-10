package by.n1jel.auction.exception;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }

    public EmptyFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
