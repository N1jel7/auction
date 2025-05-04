package by.n1jel.auction.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LotUpdateRequestDto {
    @NotNull(message = "Id is null")
    private Long id;
    @Size(min = 3, max = 20, message = "Name should be in range from 3 to 20 symbols")
    private String name;
    @Size(min = 2, max = 15, message = "Type should be in range from 3 to 20 symbols")
    private String type;
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;
}
