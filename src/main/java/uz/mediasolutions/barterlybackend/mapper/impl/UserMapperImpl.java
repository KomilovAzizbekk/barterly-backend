package uz.mediasolutions.barterlybackend.mapper.impl;

import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.mapper.abs.UserMapper;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

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
                .email(user.getEmail())
                .phoneNumber(user.getPassword())
                .username(user.getUsername())
                .build();
    }

    @Override
    public UserResDTO toResDto(User user) {
        if (user == null) {
            return null;
        }
        return UserResDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .userType(user.getUserType().getName().name())
                .build();
    }

    @Override
    public AdminResDTO toAdminResDto(User user) {
        if (user == null) {
            return null;
        }
        return AdminResDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().getName().name())
                .build();
    }
}
