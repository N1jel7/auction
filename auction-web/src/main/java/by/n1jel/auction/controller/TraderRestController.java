package by.n1jel.auction.controller;

import by.n1jel.auction.dto.*;
import by.n1jel.auction.service.TraderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/traders")
public class TraderRestController {

    private final TraderService traderService;

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Page<TraderResponseDto> getAll(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return traderService.getAllTraders(pageNumber, pageSize);
    }

    @PostMapping
    public TraderResponseDto create(@RequestBody @Validated TraderCreateRequest traderCreateRequest) {
        return traderService.createTrader(traderCreateRequest);
    }

    @PatchMapping("/{id}")
    public TraderResponseDto update(@PathVariable Long id, @RequestBody @Validated TraderUpdateRequest traderUpdateRequest) {
        return traderService.editTrader(id, traderUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public TraderResponseDto delete(@PathVariable Long id) {
        return traderService.deleteTrader(id);
    }
}
