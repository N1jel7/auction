package by.n1jel.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LotResponseDto(
        Long id,
        String name,
        String type,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime soldAt,
        Long sellerId,
        Long buyerId,
        String buyerFullname,
        String sellerFullname,
        Integer totalPagesCount
) {
}
