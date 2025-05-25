package by.n1jel.auction.web;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lots")
public class LotsRestController {

    private final LotService lotService;

    @GetMapping
    public List<LotResponseDto> getAll() {
        return lotService.getAll();
    }

    @GetMapping("/{id}")
    public LotResponseDto get(@PathVariable Long id) {
        return lotService.findLotDtoById(id);
    }

    @PostMapping
    public LotResponseDto create(@RequestBody @Validated LotCreateRequestDto lotCreateRequestDto) {
        return lotService.create(lotCreateRequestDto);
    }

    @PatchMapping("/{id}")
    public LotResponseDto update(@PathVariable Long id, @RequestBody @Validated LotUpdateRequestDto lotUpdateRequestDto) {
        return lotService.edit(id, lotUpdateRequestDto);
    }

    @DeleteMapping("/{id}")
    public LotResponseDto delete(@PathVariable Long id) {
        return lotService.delete(id);
    }
}

