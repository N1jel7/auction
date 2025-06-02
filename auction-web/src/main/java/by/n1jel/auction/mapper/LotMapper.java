package by.n1jel.auction.mapper;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
import by.n1jel.auction.entity.Lot;
import by.n1jel.auction.entity.Trader;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class LotMapper {

    @Mapping(source = "buyer", target = "buyerFullname", qualifiedByName = "mapBuyerFullname")
    @Mapping(source = "seller", target = "sellerFullname", qualifiedByName = "mapSellerFullname")
    @Mapping(source = "buyer", target = "buyerId", qualifiedByName = "mapBuyerId")
    @Mapping(source = "seller", target = "sellerId", qualifiedByName = "mapSellerId")
    public abstract LotResponseDto mapEntityToResponse(Lot lot);

    public abstract List<LotResponseDto> mapEntityToResponse(List<Lot> lots);

    public Page<LotResponseDto> mapEntityToResponse(Page<Lot> lots) {
        return new PageImpl<>(
                mapEntityToResponse(lots.toList()),
                lots.getPageable(),
                lots.getTotalElements()
        );
    }

    public abstract Lot update(@MappingTarget Lot lot, LotUpdateRequestDto updateRequestDto);

    public abstract Lot mapToEntity(LotCreateRequestDto lotCreateRequestDto);

    @Named("mapBuyerFullname")
    String getBuyerFullname(Trader buyer) {
        if (buyer != null) {
            return buyer.getSurname() + " " + buyer.getName() + " " + buyer.getPatronymic();
        }
        return null;
    }

    @Named("mapSellerFullname")
    String getSellerFullname(Trader seller) {
        if (seller != null) {
            return seller.getSurname() + " " + seller.getName() + " " + seller.getPatronymic();
        }
        return null;
    }

    @Named("mapBuyerId")
    Long getBuyerId(Trader buyer) {
        if (buyer == null) {
            return null;
        }
        return buyer.getId();
    }

    @Named("mapSellerId")
    Long getSellerId(Trader seller) {
        if (seller == null) {
            return null;
        }
        return seller.getId();
    }
}
