package uz.mediasolutions.barterlybackend.mapper.impl;

import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.mapper.abs.UserMapper;
import uz.mediasolutions.barterlybackend.payload.UserDTO;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }
}
