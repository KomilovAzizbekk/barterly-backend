package uz.mediasolutions.barterlybackend.mapper.impl;

import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.mapper.abs.RoleMapper;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleResDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        return RoleResDTO.builder()
                .id(role.getId())
                .name(role.getName().name())
                .build();
    }
}
