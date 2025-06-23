package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.entity.Lot;
import by.n1jel.auction.entity.Trader;
import by.n1jel.auction.exception.LotNotFoundException;
import by.n1jel.auction.mapper.LotMapper;
import by.n1jel.auction.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LotServiceImpl implements LotService {

    private final TraderService traderService;
    private final LotRepository lotRepository;
    private final LotMapper lotMapper;


    @Override
    public List<LotResponseDto> getAllFromPeriod(LocalDate from, LocalDate to) {
        return lotMapper.mapEntityToResponse(lotRepository.findLotsInPeriod(from.atStartOfDay(), to.atStartOfDay()));
    }

    @Override
    public Page<LotResponseDto> getAll(int pageNumber, int pageSize) {
        Page<Lot> lotsPage = lotRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return lotMapper.mapEntityToResponse(lotsPage);
    }

    @Override
    public List<LotResponseDto> getLotsByType(String type) {
        List<Lot> lotsPage = lotRepository.findLotsByType(type);
        return lotMapper.mapEntityToResponse(lotsPage);
    }

    @Override
    public List<LotResponseDto> getLotsByNameContains(String nameContaining) {
        List<Lot> lotsPage = lotRepository.findLotsByNameContaining(nameContaining);
        return lotMapper.mapEntityToResponse(lotsPage);
    }

    @Override
    public Page<LotResponseDto> getAllActive(int pageNumber, int pageSize) {
        Page<Lot> lotsPage = lotRepository.findLotsBySoldFalse(PageRequest.of(pageNumber, pageSize));
        if (lotsPage == null) {
            return null;
        }
        return lotMapper.mapEntityToResponse(lotsPage);
    }

    @Override
    public List<LotResponseDto> getActiveWithPriceRange(BigDecimal min, BigDecimal max) {
        List<Lot> lotsPage = lotRepository.findLotsBySoldFalseAndPriceIsBetween(min, max);
        return lotMapper.mapEntityToResponse(lotsPage);
    }

    @Override
    public Page<LotResponseDto> getAllSold(int pageNumber, int pageSize) {
        Page<Lot> soldLots = lotRepository.findLotsBySoldTrue(PageRequest.of(pageNumber, pageSize));
        if (soldLots == null) {
            return null;
        }
        return lotMapper.mapEntityToResponse(soldLots);
    }

    @Override
    public List<LotResponseDto> getSoldWithPriceRange(BigDecimal min, BigDecimal max) {
        List<Lot> lotsPage = lotRepository.findLotsBySoldTrueAndPriceIsBetween(min, max);
        return lotMapper.mapEntityToResponse(lotsPage);
    }


    @Override
    public LotResponseDto findLotDtoById(Long id) {
        return lotMapper.mapEntityToResponse(findLotById(id));
    }

    @Override
    public Lot findLotById(Long id) {
        return lotRepository.findLotById(id).orElseThrow(() -> new LotNotFoundException("Lot with id " + id + " not found"));
    }

    @Override
    public LotResponseDto create(LotCreateRequestDto lotCreateRequestDto) {
        Lot lot = lotMapper.mapToEntity(lotCreateRequestDto);
        return lotMapper.mapEntityToResponse(lotRepository.save(lot));
    }

    @Override
    public LotResponseDto edit(Long id, LotUpdateRequestDto lotUpdateRequestDto) {
        Lot lot = findLotById(id);
        Trader seller = null;
        Trader buyer = null;

        lot = lotMapper.update(lot, lotUpdateRequestDto);

        if (lotUpdateRequestDto.sellerId() != null) {
            seller = traderService.findTraderById(lotUpdateRequestDto.sellerId());
            lot.setSeller(seller);
        }

        if (lotUpdateRequestDto.sellerId() != null && lotUpdateRequestDto.buyerId() != null) {
            buyer = traderService.findTraderById(lotUpdateRequestDto.buyerId());
            lot.setBuyer(buyer);
            lot.setSold(true);
            lot.setSoldAt(LocalDateTime.now());
        }

        return lotMapper.mapEntityToResponse(lotRepository.save(lot));
    }

    @Override
    public LotResponseDto delete(Long id) {
        Lot lot = findLotById(id);
        lotRepository.deleteById(id);
        return lotMapper.mapEntityToResponse(lot);
    }
}
