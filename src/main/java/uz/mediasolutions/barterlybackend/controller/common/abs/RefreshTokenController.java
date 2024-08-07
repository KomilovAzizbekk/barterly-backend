package uz.mediasolutions.barterlybackend.controller.common.abs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "token")
public interface RefreshTokenController {

    @PostMapping("/refresh-token")
    ResponseEntity<TokenDTO> refresh(HttpServletRequest request);
}
