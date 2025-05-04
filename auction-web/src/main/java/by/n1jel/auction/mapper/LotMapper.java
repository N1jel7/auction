package by.n1jel.auction.mapper;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.model.Lot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LotMapper {

    LotResponseDto mapToResponse(Lot lot);

    List<LotResponseDto> mapToResponse(Collection<Lot> lots);

    void update(@MappingTarget Lot lot, LotUpdateRequestDto updateRequestDto);


    Lot mapToEntity(LotCreateRequestDto lotCreateRequestDto);

    List<Lot> mapToEntity(List<LotCreateRequestDto> lotCreateRequestDtos);

}
