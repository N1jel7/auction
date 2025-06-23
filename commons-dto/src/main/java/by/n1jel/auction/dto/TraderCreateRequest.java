package by.n1jel.auction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TraderCreateRequest(
        @NotBlank(message = "Surname must not be blank")
        @Size(min = 3, max = 20, message = "Surname must be in range from 3 to 20 symbols")
        String surname,
        @NotBlank(message = "Name must not be blank")
        @Size(min = 2, max = 20, message = "Name must be in range from 2 to 20 symbols")
        String name,
        @NotBlank(message = "Patronymic must not be blank")
        @Size(min = 3, max = 20, message = "Patronymic must be in range from 3 to 20 symbols")
        String patronymic
) {
}
