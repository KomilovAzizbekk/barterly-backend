package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.payload.UserDTO;

public interface UserMapper {

    UserDTO toDto(User user);
}
