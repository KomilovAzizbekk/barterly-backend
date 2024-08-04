package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.UserController;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.request.AdminReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;
import uz.mediasolutions.barterlybackend.service.abs.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDTO> getMe() {
        return userService.getMe();
    }

    @Override
    public ResponseEntity<Page<UserResDTO>> getAllUsers(String search, int page, int size) {
        return userService.getAllUsers(search, page, size);
    }

    @Override
    public ResponseEntity<Page<AdminResDTO>> getAllAdmins(String search, int page, int size) {
        return userService.getAllAdmins(search, page, size);
    }

    @Override
    public ResponseEntity<?> addAdmin(AdminReqDTO dto) {
        return userService.addAdmin(dto);
    }

    @Override
    public ResponseEntity<?> editAdmin(UUID id, AdminReqDTO dto) {
        return userService.editAdmin(id, dto);
    }

    @Override
    public ResponseEntity<?> deleteAdmin(UUID id) {
        return userService.deleteAdmin(id);
    }

}
