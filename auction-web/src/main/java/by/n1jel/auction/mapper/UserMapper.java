package by.n1jel.auction.mapper;

import by.n1jel.auction.dto.RegistrationRequest;
import by.n1jel.auction.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User mapToUser(RegistrationRequest registrationRequest);

    List<User> mapToUser(List<RegistrationRequest> registrationRequestList);


}
