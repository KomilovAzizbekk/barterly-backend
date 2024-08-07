package uz.mediasolutions.barterlybackend.controller.common.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.common.abs.RefreshTokenController;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.service.common.abs.RefreshTokenService;

@RestController
@RequiredArgsConstructor
public class RefreshTokenControllerImpl implements RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<TokenDTO> refresh(HttpServletRequest request) {
        return refreshTokenService.refreshToken(request);
    }

}
