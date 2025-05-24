package by.n1jel.auction.dto;

public record RegistrationRequest(
        String username,
        String password,
        String email
) {
}
