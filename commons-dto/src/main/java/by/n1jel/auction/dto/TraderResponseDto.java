package by.n1jel.auction.dto;

import java.time.LocalDateTime;

public record TraderResponseDto(
        Long id,
        String surname,
        String name,
        String patronymic,
        Long lotId,
        Integer totalPagesCount
) {
}
