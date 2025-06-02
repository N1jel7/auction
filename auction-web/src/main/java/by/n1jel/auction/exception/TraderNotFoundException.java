package by.n1jel.auction.exception;

public class TraderNotFoundException extends RuntimeException{

    public TraderNotFoundException(String message) {
        super(message);
    }

    public TraderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
