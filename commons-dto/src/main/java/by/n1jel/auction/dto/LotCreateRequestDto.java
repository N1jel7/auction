package by.n1jel.auction.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LotCreateRequestDto(
        @Size(min = 3, max = 20, message = "Name should be in range from 3 to 20 symbols")
        String name,
        @Size(min = 2, max = 15, message = "Type should be in range from 3 to 20 symbols")
        String type,
        @Digits(integer = 7, fraction = 2, message = "Value should be in range from 1 to 9999999 and max 2 fractions")
        BigDecimal price
) {
}
