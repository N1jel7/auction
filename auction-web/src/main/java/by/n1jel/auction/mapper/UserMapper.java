package by.n1jel.auction.mapper;

import by.n1jel.auction.dto.RegistrationRequestDto;
import by.n1jel.auction.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User mapToUser(RegistrationRequestDto registrationRequest);

    List<User> mapToUser(List<RegistrationRequestDto> registrationRequestList);


}
