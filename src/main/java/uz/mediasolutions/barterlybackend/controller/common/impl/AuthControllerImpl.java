package uz.mediasolutions.barterlybackend.controller.common.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.common.abs.AuthController;
import uz.mediasolutions.barterlybackend.payload.SignInAdminDTO;
import uz.mediasolutions.barterlybackend.payload.SignInUserDTO;
import uz.mediasolutions.barterlybackend.payload.SignUpUserDTO;
import uz.mediasolutions.barterlybackend.payload.response.SignUpResDTO;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.service.common.abs.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<SignUpResDTO> signUpUser(@RequestBody SignUpUserDTO dto, HttpSession session) {
        return authService.signUpUser(dto, session);
    }

    @Override
    public ResponseEntity<?> signInUser(@RequestBody SignInUserDTO dto) {
        return authService.signInUser(dto);
    }

    @Override
    public ResponseEntity<TokenDTO> signInAdmin(@RequestBody SignInAdminDTO dto) {
        return authService.signInAdmin(dto);
    }

    @Override
    public ResponseEntity<?> logout() {
        return authService.logout();
    }

}
