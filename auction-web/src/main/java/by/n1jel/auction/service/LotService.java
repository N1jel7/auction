package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.model.Lot;

import java.util.List;

public interface LotService {

    List<LotResponseDto> getAll();
    LotResponseDto get(Long id);
    LotResponseDto create(LotCreateRequestDto lotCreateRequestDto);
    LotResponseDto edit(LotUpdateRequestDto lotUpdateRequestDto);
    LotResponseDto delete(Long id);

}
