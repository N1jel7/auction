package by.n1jel.auction.dto;

import lombok.Builder;

public record ErrorResponse(
        long status,
        String error,
        String description
) {
}
