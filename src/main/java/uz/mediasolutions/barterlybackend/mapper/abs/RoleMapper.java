package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;

public interface RoleMapper {

    RoleResDTO toDTO(Role role);

}
