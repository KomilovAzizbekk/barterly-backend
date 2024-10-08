package uz.mediasolutions.barterlybackend.service.common.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.RefreshToken;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.repository.RefreshTokenRepository;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.secret.JwtService;
import uz.mediasolutions.barterlybackend.service.common.abs.RefreshTokenService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<TokenDTO> refreshToken(HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final UUID userId;

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw RestException.notFound("Authorization header is missing");
        }
        refreshToken = authorization.substring(7);

        userId = jwtService.extractUserId(refreshToken);
        if (userId == null) {
            throw RestException.restThrow("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findFirstByIdAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(userId);
        if (optionalUser.isEmpty() || !refreshTokenRepository.existsByUserId(optionalUser.get().getId())) {
            throw RestException.restThrow("User not found with this token", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        if (!passwordEncoder.matches(refreshToken, token.getToken())) {
            throw RestException.restThrow("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        if (jwtService.isTokenValid(refreshToken, user)) {
            String accessToken = jwtService.generateToken(user);
            var tokenDTO = new TokenDTO("Bearer", accessToken, refreshToken);
            return ResponseEntity.ok(tokenDTO);
        } else {
            throw RestException.restThrow("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }
    }


}
