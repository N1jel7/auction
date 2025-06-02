package by.n1jel.auction.dto;

import java.time.LocalDate;

public record ReportRequestDto(
        LocalDate from,
        LocalDate to
) {
}
