package uz.mediasolutions.barterlybackend.service.common.abs;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.SignInAdminDTO;
import uz.mediasolutions.barterlybackend.payload.SignInUserDTO;
import uz.mediasolutions.barterlybackend.payload.SignUpUserDTO;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.payload.response.SignUpResDTO;

public interface AuthService {

    ResponseEntity<TokenDTO> signInAdmin(SignInAdminDTO adminDTO);

    ResponseEntity<?> signInUser(SignInUserDTO userDTO);

    ResponseEntity<SignUpResDTO> signUpUser(SignUpUserDTO dto, HttpSession session);

    ResponseEntity<?> logout();

}
