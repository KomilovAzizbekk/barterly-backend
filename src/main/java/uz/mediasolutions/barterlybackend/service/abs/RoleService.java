package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.response.RoleResDTO;

import java.util.List;

public interface RoleService {

    ResponseEntity<List<RoleResDTO>> findAll();

    ResponseEntity<List<RoleResDTO>> getAllAdminRoles();

}
