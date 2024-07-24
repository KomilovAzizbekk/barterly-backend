package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

public interface UserMapper {

    UserDTO toDto(User user);

    UserResDTO toResDto(User user);

    AdminResDTO toAdminResDto(User user);

}
