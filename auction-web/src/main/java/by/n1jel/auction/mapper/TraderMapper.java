package by.n1jel.auction.mapper;

import by.n1jel.auction.dto.TraderCreateRequest;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.dto.TraderUpdateRequest;
import by.n1jel.auction.entity.Trader;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TraderMapper {

    TraderResponseDto mapToResponse(Trader trader);

    List<TraderResponseDto> mapToResponse(List<Trader> traders);

    default Page<TraderResponseDto> mapToResponse(Page<Trader> traders) {
        return new PageImpl<>(
                mapToResponse(traders.toList()),
                traders.getPageable(),
                traders.getTotalElements()
        );
    }

    Trader update(@MappingTarget Trader trader, TraderUpdateRequest traderUpdateRequest);

    Trader mapToEntity(TraderCreateRequest traderCreateRequest);
}
