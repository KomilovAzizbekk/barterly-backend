package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.request.AdminReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

public interface UserService {

    ResponseEntity<UserDTO> getMe();

    ResponseEntity<Page<UserResDTO>> getAllUsers(int page, int size);

    ResponseEntity<Page<AdminResDTO>> getAllAdmins(int page, int size);

    ResponseEntity<?> addAdmin(AdminReqDTO dto);

}
