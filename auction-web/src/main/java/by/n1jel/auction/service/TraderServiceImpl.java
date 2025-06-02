package by.n1jel.auction.service;

import by.n1jel.auction.dto.TraderCreateRequest;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.dto.TraderUpdateRequest;
import by.n1jel.auction.entity.Trader;
import by.n1jel.auction.exception.TraderNotFoundException;
import by.n1jel.auction.mapper.TraderMapper;
import by.n1jel.auction.repository.TraderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TraderServiceImpl implements TraderService {

    private final TraderRepository traderRepository;
    private final TraderMapper traderMapper;

    @Override
    public Page<TraderResponseDto> getAllTraders(int pageNumber, int pageSize) {
        Page<Trader> tradersPage = traderRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return traderMapper.mapToResponse(tradersPage);
    }

    @Override
    public Trader findTraderById(Long id) {
        return traderRepository.findTraderById(id).orElseThrow(() -> new TraderNotFoundException("Trader with id " + id + " not found"));
    }

    @Override
    public TraderResponseDto createTrader(TraderCreateRequest traderCreateRequest) {
        Trader trader = traderMapper.mapToEntity(traderCreateRequest);
        return traderMapper.mapToResponse(traderRepository.save(trader));
    }

    @Override
    public TraderResponseDto editTrader(Long id, TraderUpdateRequest traderUpdateRequest) {
        Trader trader = findTraderById(id);
        trader = traderMapper.update(trader, traderUpdateRequest);
        return traderMapper.mapToResponse(traderRepository.save(trader));
    }

    @Override
    public TraderResponseDto deleteTrader(Long id) {
        Trader trader = findTraderById(id);
        traderRepository.deleteById(id);
        return traderMapper.mapToResponse(trader);
    }
}
