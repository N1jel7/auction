package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.entity.Lot;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface LotService {

    List<LotResponseDto> getAllFromPeriod(LocalDate from, LocalDate to);

    Page<LotResponseDto> getAll(int pageNumber, int pageSize);

    List<LotResponseDto> getLotsByType(String type);

    List<LotResponseDto> getLotsByNameContains(String nameContaining);

    Page<LotResponseDto> getAllActive(int pageNumber, int pageSize);

    List<LotResponseDto> getActiveWithPriceRange(BigDecimal min, BigDecimal max);

    Page<LotResponseDto> getAllSold(int pageNumber, int pageSize);

    List<LotResponseDto> getSoldWithPriceRange(BigDecimal min, BigDecimal max);

    LotResponseDto findLotDtoById(Long id);

    Lot findLotById(Long id);

    LotResponseDto create(LotCreateRequestDto lotCreateRequestDto);

    LotResponseDto edit(Long id, LotUpdateRequestDto lotUpdateRequestDto);

    LotResponseDto delete(Long id);
}
