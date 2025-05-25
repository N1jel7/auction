package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.entity.Lot;
import by.n1jel.auction.exception.LotNotFoundException;
import by.n1jel.auction.mapper.LotMapper;
import by.n1jel.auction.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final LotMapper lotMapper;


    @Override
    public List<LotResponseDto> getAll() {
        return lotMapper.mapToResponse(lotRepository.findAll());
    }

    @Override
    public LotResponseDto findLotDtoById(Long id) {
        return lotMapper.mapToResponse(findLotById(id));
    }

    @Override
    public Lot findLotById(Long id) {
        return lotRepository.findLotById(id).orElseThrow(() -> new LotNotFoundException("Lot with id " + id + " not found"));
    }

    @Override
    public LotResponseDto create(LotCreateRequestDto lotCreateRequestDto) {
        Lot lot = lotMapper.mapToEntity(lotCreateRequestDto);
        lotRepository.save(lot);
        return lotMapper.mapToResponse(lot);
    }

    @Override
    public LotResponseDto edit(Long id, LotUpdateRequestDto lotUpdateRequestDto) {
        Lot lot = findLotById(id);
        lot = lotMapper.update(lot, lotUpdateRequestDto);
        return lotMapper.mapToResponse(lot);
    }

    @Override
    public LotResponseDto delete(Long id) {
        Lot lot = findLotById(id);
        lotRepository.deleteById(id);
        return lotMapper.mapToResponse(lot);
    }
}
