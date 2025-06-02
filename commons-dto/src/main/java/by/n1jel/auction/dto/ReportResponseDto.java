package by.n1jel.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public record ReportResponseDto(
        LocalDate from,
        LocalDate to,
        List<LotResponseDto> lots,
        BigDecimal totalLotsPrice,
        Integer totalLotsAmount,
        Long periodInDays,
        HashMap<Integer, BigDecimal> dailyPrice
) {
}
