package uz.mediasolutions.barterlybackend.service.abs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;

public interface RefreshTokenService {

    ResponseEntity<TokenDTO> refreshToken(HttpServletRequest request);

}
