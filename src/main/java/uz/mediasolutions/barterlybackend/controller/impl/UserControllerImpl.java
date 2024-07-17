package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.abs.UserController;
import uz.mediasolutions.barterlybackend.payload.UserDTO;
import uz.mediasolutions.barterlybackend.payload.request.AdminReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;
import uz.mediasolutions.barterlybackend.service.abs.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDTO> getMe() {
        return userService.getMe();
    }

    @Override
    public ResponseEntity<Page<UserResDTO>> getAllUsers(int page, int size) {
        return userService.getAllUsers(page, size);
    }

    @Override
    public ResponseEntity<Page<AdminResDTO>> getAllAdmins(int page, int size) {
        return userService.getAllAdmins(page, size);
    }

    @Override
    public ResponseEntity<?> addAdmin(AdminReqDTO dto) {
        return userService.addAdmin(dto);
    }

}
