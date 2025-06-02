package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.TraderCreateRequest;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.dto.TraderUpdateRequest;
import by.n1jel.auction.utils.CustomPageImpl;

import java.util.List;

public interface TraderClientService {

    CustomPageImpl<TraderResponseDto> findAllTraders(int pageNumber, int pageSize);
    TraderResponseDto createTrader(TraderCreateRequest traderCreateRequest);
    TraderResponseDto updateTrader(Long id, TraderUpdateRequest traderUpdateRequest);
    TraderResponseDto deleteTrader(Long id);

}
