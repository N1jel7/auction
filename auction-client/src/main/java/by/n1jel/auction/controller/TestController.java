package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.service.AuctionLotClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {
    private final AuctionLotClientService clientService;

    @GetMapping("/find/all")
    public List<LotResponseDto> findAll(){
        return clientService.findAll();
    }

    @GetMapping("/find/{id}")
    public LotResponseDto findById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @PostMapping("/create")
    public LotResponseDto create(@RequestBody LotCreateRequestDto lotCreateRequestDto) {
        return clientService.create(lotCreateRequestDto);
    }

    @PatchMapping("/update/{id}")
    public LotResponseDto update(@PathVariable Long id, @RequestBody LotUpdateRequestDto lotUpdateRequestDto) {
        return clientService.updateById(id, lotUpdateRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public LotResponseDto delete(@PathVariable Long id) {
        return clientService.deleteById(id);
    }
}
