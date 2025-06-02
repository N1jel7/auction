package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.ReportRequestDto;
import by.n1jel.auction.dto.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final LotService lotService;


    private HashMap<Integer, BigDecimal> getDailyPrice(List<LotResponseDto> dtos, LocalDate from, LocalDate to) {
        HashMap<Integer, BigDecimal> dailyPrice = new HashMap<>();
        long daysAmount = getDaysFromPeriod(from, to);

        // from to period in days
        for (int i = 1; i < daysAmount; i++) {
            List<LotResponseDto> thatDay = new ArrayList<>();

            // search for lots sold on this day
            for (LotResponseDto lot : dtos) {

                // converting local date time to local date
                LocalDate lotSoldAt = lot.soldAt().toLocalDate();

                // if lot sold time equals our day from cycle add to thatDay list
                if (lotSoldAt.isEqual(from) && lotSoldAt.isBefore(to)) {
                    thatDay.add(lot);
                }
            }

            dailyPrice.put(i, getTotalLotsPrice(thatDay));
            from.plusDays(1);
        }

        return dailyPrice;
    }

    private BigDecimal getTotalLotsPrice(List<LotResponseDto> dtos) {
        BigDecimal totalLotsPrice = BigDecimal.ZERO;
        for (LotResponseDto lot : dtos) {
            totalLotsPrice = totalLotsPrice.add(lot.price());
        }
        return totalLotsPrice;
    }

    private Long getDaysFromPeriod(LocalDate from, LocalDate to) {
        return ChronoUnit.DAYS.between(from, to);
    }

    @Override
    public ReportResponseDto getReport(ReportRequestDto reportRequestDto) {
        LocalDate from = reportRequestDto.from();
        LocalDate to = reportRequestDto.to();
        List<LotResponseDto> lotsList = lotService.getAllFromPeriod(from, to);

        return new ReportResponseDto(
                reportRequestDto.from(),
                reportRequestDto.to(),
                lotsList,
                getTotalLotsPrice(lotsList),
                lotsList.size(),
                getDaysFromPeriod(from, to),
                getDailyPrice(lotsList, from, to)
        );
    }
}
