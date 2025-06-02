package by.n1jel.auction.exception;

public class LotNotFoundException extends RuntimeException{

    public LotNotFoundException(String message) {
        super(message);
    }

    public LotNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
