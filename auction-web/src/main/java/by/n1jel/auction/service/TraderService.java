package by.n1jel.auction.service;

import by.n1jel.auction.dto.TraderCreateRequest;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.dto.TraderUpdateRequest;
import by.n1jel.auction.entity.Trader;
import org.springframework.data.domain.Page;

public interface TraderService {

    Trader findTraderById(Long id);

    Page<TraderResponseDto> getAllTraders(int pageNumber, int pageSize);

    TraderResponseDto createTrader(TraderCreateRequest traderCreateRequest);

    TraderResponseDto editTrader(Long id, TraderUpdateRequest traderUpdateRequest);

    TraderResponseDto deleteTrader(Long id);
}
