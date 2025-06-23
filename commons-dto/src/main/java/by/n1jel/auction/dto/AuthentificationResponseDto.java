package by.n1jel.auction.dto;

public record AuthentificationResponseDto(
        String accessToken,
        String refreshToken
) {
}
