package by.n1jel.auction.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDto(
        @NotBlank(message = "Username must not be blank")
        @Size(min = 3, max = 20, message = "Username should be in range from 3 to 20 symbols")
        String username,
        @NotBlank(message = "Password must not be blank")
        @Size(min = 5, max = 25, message = "Password should be in range from 5 to 25 symbols")
        String password,
        @NotBlank(message = "Email must not be blank")
        @Email(regexp = ".+[@].+[\\.].+", message = "Incorrect email address")
        String email
) {
}
