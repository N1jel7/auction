package by.n1jel.auction.exception;

import lombok.Getter;

@Getter
public class UiAlertException extends RuntimeException {
    private final String description;


    public UiAlertException(String message, Throwable cause, String description) {
        super(message, cause);
        this.description = description;
    }

    public UiAlertException(String message, String description) {
        super(message);
        this.description = description;
    }
}
