package by.n1jel.auction.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserDto (
        Long id,
        @Size(min = 3, max = 20, message = "Username should be in range from 3 to 20 symbols")
        String username,
        @Email(regexp = ".+[@].+[\\.].+", message = "Incorrect email address")
        String email
) {
}
