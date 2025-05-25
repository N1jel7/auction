package by.n1jel.auction.dto;

import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @Size(min = 3, max = 20, message = "Username should be in range from 3 to 20 symbols")
        String username,
        @Size(min = 5, max = 25, message = "Password should be in range from 5 to 25 symbols")
        String password
) {
}
