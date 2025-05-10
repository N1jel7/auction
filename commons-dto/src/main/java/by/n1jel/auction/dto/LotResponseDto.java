package by.n1jel.auction.dto;

import java.math.BigDecimal;

public record LotResponseDto(
        Long id,
        String name,
        String type,
        BigDecimal price
) {
}
