package uz.mediasolutions.barterlybackend.controller.common.abs;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.SignInAdminDTO;
import uz.mediasolutions.barterlybackend.payload.SignInUserDTO;
import uz.mediasolutions.barterlybackend.payload.SignUpUserDTO;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.payload.response.SignUpResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "auth")
public interface AuthController {

    @PostMapping("/sign-up")
    ResponseEntity<SignUpResDTO> signUpUser(@RequestBody SignUpUserDTO dto, HttpSession session);

    @PostMapping("/sign-in")
    ResponseEntity<?> signInUser(@RequestBody SignInUserDTO dto);

    @PostMapping("/admin/sign-in")
    ResponseEntity<TokenDTO> signInAdmin(@RequestBody SignInAdminDTO dto);

    @PostMapping("/logout")
    ResponseEntity<?> logout();

}