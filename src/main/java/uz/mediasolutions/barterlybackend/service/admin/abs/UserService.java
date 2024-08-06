package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.request.AdminReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

import java.util.UUID;

public interface UserService {

    ResponseEntity<UserDTO> getMe();

    ResponseEntity<Page<UserResDTO>> getAllUsers(String search, int page, int size);

    ResponseEntity<Page<AdminResDTO>> getAllAdmins(String search, int page, int size);

    ResponseEntity<?> addAdmin(AdminReqDTO dto);

    ResponseEntity<?> editAdmin(UUID id, AdminReqDTO dto);

    ResponseEntity<?> deleteAdmin(UUID id);


}
