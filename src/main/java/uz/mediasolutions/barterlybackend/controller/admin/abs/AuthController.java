package uz.mediasolutions.barterlybackend.controller.admin.abs;

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
    ResponseEntity<SignUpResDTO> signUpUser(@RequestBody SignUpUserDTO dto);

    @PostMapping("/sign-in")
    ResponseEntity<TokenDTO> signInUser(@RequestBody SignInUserDTO dto);

    @PostMapping("/admin/sign-in")
    ResponseEntity<TokenDTO> signInAdmin(@RequestBody SignInAdminDTO dto);

    @PostMapping("/logout")
    ResponseEntity<?> logout(@RequestBody SignInUserDTO dto);

}
