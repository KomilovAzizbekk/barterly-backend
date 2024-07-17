package uz.mediasolutions.barterlybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    @Mapping(target = "userType", source = "userType.name")
    UserResDTO toResDto(User user);

    @Mapping(target = "role", source = "role.name")
    AdminResDTO toAdminResDto(User user);

}
