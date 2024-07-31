package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.AuthController;
import uz.mediasolutions.barterlybackend.payload.SignInAdminDTO;
import uz.mediasolutions.barterlybackend.payload.SignInUserDTO;
import uz.mediasolutions.barterlybackend.payload.SignUpUserDTO;
import uz.mediasolutions.barterlybackend.payload.response.SignUpResDTO;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.service.abs.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<SignUpResDTO> signUpUser(@RequestBody SignUpUserDTO dto) {
        return authService.signUpUser(dto);
    }

    @Override
    public ResponseEntity<TokenDTO> signInUser(@RequestBody SignInUserDTO dto) {
        return authService.signInUser(dto);
    }

    @Override
    public ResponseEntity<TokenDTO> signInAdmin(@RequestBody SignInAdminDTO dto) {
        return authService.signInAdmin(dto);
    }

    @Override
    public ResponseEntity<?> logout(@RequestBody SignInUserDTO dto) {
        return authService.logout();
    }

}
