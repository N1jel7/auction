package by.n1jel.auction.service;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.exception.LotNotFoundException;
import by.n1jel.auction.mapper.LotMapper;
import by.n1jel.auction.model.Lot;
import by.n1jel.auction.utils.LotIdGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LotServiceImpl implements LotService {

    private final Map<Long, Lot> lots = new HashMap<>();
    private final LotMapper lotMapper;
    private final LotIdGenerator lotIdGenerator;


    @Override
    public List<LotResponseDto> getAll() {
        return lotMapper.mapToResponse(lots.values());
    }

    @Override
    public LotResponseDto get(Long id) {
        if(lots.get(id) == null){
            throw new LotNotFoundException("Lot not found", new Throwable("Lot with id " + id + " not found."));
        }
        return lotMapper.mapToResponse(lots.get(id));
    }

    @Override
    public LotResponseDto create(LotCreateRequestDto lotCreateRequestDto) {
        Lot lot = lotMapper.mapToEntity(lotCreateRequestDto);
        Long lotId = lotIdGenerator.getId();
        lot.setId(lotId);
        lots.put(lotId, lot);
        return lotMapper.mapToResponse(lot);
    }

    @Override
    public LotResponseDto edit(LotUpdateRequestDto lotUpdateRequestDto) {
        Lot lot = lots.get(lotUpdateRequestDto.getId());
        if(lot != null){
            lotMapper.update(lot, lotUpdateRequestDto);
        }
        return lotMapper.mapToResponse(lot);
    }

    @Override
    public LotResponseDto delete(Long id) {
        Lot lot = lots.get(id);
        lots.remove(id);
        return lotMapper.mapToResponse(
                lot
        );
    }
}
