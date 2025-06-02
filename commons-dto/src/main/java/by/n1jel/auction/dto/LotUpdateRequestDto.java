package by.n1jel.auction.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LotUpdateRequestDto(
    @Size(min = 3, max = 20, message = "Name should be in range from 3 to 20 symbols")
    String name,
    @Size(min = 2, max = 15, message = "Type should be in range from 3 to 20 symbols")
    String type,
    @Digits(integer = 7, fraction = 2)
    BigDecimal price,
    Long sellerId,
    Long buyerId
    ){
}
