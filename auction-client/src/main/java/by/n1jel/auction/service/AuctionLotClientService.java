package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import javafx.scene.control.TextField;

import java.util.List;

public interface AuctionLotClientService {
    List<LotResponseDto> findAll();
    LotResponseDto findById(Long id);
    LotResponseDto create(LotCreateRequestDto lotCreateRequestDto);
    LotResponseDto updateById(Long id, LotUpdateRequestDto lotUpdateRequestDto);
    LotResponseDto deleteById(Long id);
    LotCreateRequestDto mapFieldsToCreateDto(TextField name, TextField price, TextField type);
}
