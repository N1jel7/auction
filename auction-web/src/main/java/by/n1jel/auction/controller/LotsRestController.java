package by.n1jel.auction.controller;

import by.n1jel.auction.dto.*;
import by.n1jel.auction.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lots")
public class LotsRestController {

    private final LotService lotService;

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Page<LotResponseDto> getAll(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return lotService.getAll(pageNumber, pageSize);
    }

    @GetMapping("/active/page/{pageNumber}/{pageSize}")
    public Page<LotResponseDto> getActive(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return lotService.getAllActive(pageNumber, pageSize);
    }

    @GetMapping("/sold/page/{pageNumber}/{pageSize}")
    public Page<LotResponseDto> getSold(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return lotService.getAllSold(pageNumber, pageSize);
    }

    @GetMapping("/active/range/{min}/{max}")
    public List<LotResponseDto> getActiveWithPriceRange(
            @PathVariable BigDecimal min,
            @PathVariable BigDecimal max) {
        return lotService.getActiveWithPriceRange(min, max);
    }

    @GetMapping("/sold/range/{min}/{max}")
    public List<LotResponseDto> getSoldWithPriceRange(
            @PathVariable BigDecimal min,
            @PathVariable BigDecimal max) {
        return lotService.getSoldWithPriceRange(min, max);
    }

    @GetMapping("/name/contains/{containing}")
    public List<LotResponseDto> getByNameContaining(
            @PathVariable String containing) {
        return lotService.getLotsByNameContains(containing);
    }

    @GetMapping("/type/{type}")
    public List<LotResponseDto> getByType(
            @PathVariable String type) {
        return lotService.getLotsByType(type);
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

