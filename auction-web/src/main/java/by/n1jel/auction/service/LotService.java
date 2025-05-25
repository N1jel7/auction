package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.entity.Lot;

import java.util.List;

public interface LotService {

    List<LotResponseDto> getAll();

    LotResponseDto findLotDtoById(Long id);

    Lot findLotById(Long id);

    LotResponseDto create(LotCreateRequestDto lotCreateRequestDto);

    LotResponseDto edit(Long id, LotUpdateRequestDto lotUpdateRequestDto);

    LotResponseDto delete(Long id);
}
