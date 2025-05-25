package by.n1jel.auction.dto;

public record ErrorResponseDto(
        long status,
        String error,
        String description
) {
}
