package by.n1jel.auction.service;

import by.n1jel.auction.dto.*;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.utils.CustomPageImpl;
import javafx.scene.control.TextField;

import java.util.List;

public interface AuctionLotClientService {
    CustomPageImpl<LotResponseDto> findAll(int pageNumber, int pageSize);
    LotResponseDto findById(Long id);
    LotResponseDto create(LotCreateRequestDto lotCreateRequestDto);
    LotResponseDto updateById(Long id, LotUpdateRequestDto lotUpdateRequestDto);
    LotResponseDto deleteById(Long id);
    boolean isAddressAlive(String address);
    CustomPageImpl<LotResponseDto> findAllActive(int pageNumber, int pageSize);
    CustomPageImpl<LotResponseDto> findAllSold(int pageNumber, int pageSize);
    ReportResponseDto getReport(ReportRequestDto reportRequestDto);
}
