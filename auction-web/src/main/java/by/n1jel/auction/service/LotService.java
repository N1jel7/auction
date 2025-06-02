package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.entity.Lot;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface LotService {

    List<LotResponseDto> getAllFromPeriod(LocalDate from, LocalDate to);

    Page<LotResponseDto> getAll(int pageNumber, int pageSize);

    Page<LotResponseDto> getAllActive(int pageNumber, int pageSize);

    Page<LotResponseDto> getAllSold(int pageNumber, int pageSize);

    LotResponseDto findLotDtoById(Long id);

    Lot findLotById(Long id);

    LotResponseDto create(LotCreateRequestDto lotCreateRequestDto);

    LotResponseDto edit(Long id, LotUpdateRequestDto lotUpdateRequestDto);

    LotResponseDto delete(Long id);
}
