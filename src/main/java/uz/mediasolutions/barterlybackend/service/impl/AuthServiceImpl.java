package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.RefreshToken;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.geocoding.GeocodingService;
import uz.mediasolutions.barterlybackend.payload.SignInAdminDTO;
import uz.mediasolutions.barterlybackend.payload.SignInUserDTO;
import uz.mediasolutions.barterlybackend.payload.SignUpUserDTO;
import uz.mediasolutions.barterlybackend.payload.response.SignUpResDTO;
import uz.mediasolutions.barterlybackend.payload.response.TokenDTO;
import uz.mediasolutions.barterlybackend.repository.RefreshTokenRepository;
import uz.mediasolutions.barterlybackend.repository.RoleRepository;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.repository.UserTypeRepository;
import uz.mediasolutions.barterlybackend.secret.JwtService;
import uz.mediasolutions.barterlybackend.service.OtpService;
import uz.mediasolutions.barterlybackend.service.abs.AuthService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final GeocodingService geocodingService;
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserTypeRepository userTypeRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public ResponseEntity<TokenDTO> signInAdmin(SignInAdminDTO adminDTO) {
        Optional<User> optionalUser = userRepository.findFirstByEmailAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(adminDTO.getEmail());

        if (optionalUser.isEmpty()) {
            throw RestException.restThrow("Admin not found", HttpStatus.UNAUTHORIZED);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(adminDTO.getPassword(), user.getPassword())) {
            throw RestException.restThrow("Password is incorrect", HttpStatus.UNAUTHORIZED);
        } else if (user.getAuthorities().contains(roleRepository.findByName(RoleEnum.ROLE_USER))) {
            throw RestException.restThrow("Role is incorrect", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminDTO.getEmail(), adminDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        //Saving refresh token to database
        RefreshToken token;
        if (refreshTokenRepository.existsByUserId(user.getId())) {
            token = refreshTokenRepository.findByUserId(user.getId());
            token.setExpireDate(jwtService.extractExpiration(refreshToken));
            token.setToken(refreshToken);
        } else {
            token = RefreshToken.builder()
                    .token(refreshToken)
                    .user(user)
                    .expireDate(jwtService.extractExpiration(refreshToken))
                    .build();
        }
        refreshTokenRepository.save(token);

        TokenDTO tokenDTO = new TokenDTO("Bearer", accessToken, refreshToken);
        return ResponseEntity.ok(tokenDTO);
    }


    @Override
    public ResponseEntity<TokenDTO> signInUser(SignInUserDTO userDTO) {
//        if (!otpService.validateOtp(userDTO.getPhoneNumber(), userDTO.getOtp())) {
//            throw new RuntimeException("Invalid OTP");
//        }

        //First way to sign-in (email and password)
        Optional<User> userOptional = userRepository.findFirstByEmailAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(userDTO.getEmail());
        if (userOptional.isEmpty()) {
            throw RestException.restThrow("User not found", HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw RestException.restThrow("Password is incorrect", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        //Saving refresh token to database
        RefreshToken token;
        if (refreshTokenRepository.existsByUserId(user.getId())) {
            token = refreshTokenRepository.findByUserId(user.getId());
            token.setExpireDate(jwtService.extractExpiration(refreshToken));
            token.setToken(refreshToken);
        } else {
            token = RefreshToken.builder()
                    .token(refreshToken)
                    .user(user)
                    .expireDate(jwtService.extractExpiration(refreshToken))
                    .build();
        }
        refreshTokenRepository.save(token);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new TokenDTO("Bearer", accessToken, refreshToken));
    }


    @Override
    public ResponseEntity<SignUpResDTO> signUpUser(SignUpUserDTO dto) {

        if (!dto.getPhoneNumber().matches(Rest.PHONE_NUMBER_REGEX)) {
            throw RestException.restThrow("Phone number is not valid", HttpStatus.BAD_REQUEST);
        }

        if (!dto.getEmail().matches(Rest.EMAIL_REGEX)) {
            throw RestException.restThrow("Email is not valid", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optional = userRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (optional.isPresent()) {
            throw RestException.restThrow("User already existed", HttpStatus.BAD_REQUEST);
        }

        System.out.println(geocodingService.getCountryCityAndDistrict(dto.getLat(), dto.getLon()));

        User user = User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .username(dto.getUsername())
                .balance(BigDecimal.valueOf(0))
                .userType(userTypeRepository.getReferenceById(dto.getUserTypeId()))
                .role(roleRepository.findByName(RoleEnum.ROLE_USER))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        userRepository.save(user);

//        otpService.generateOtp(dto.getPhoneNumber());

        return ResponseEntity.ok(new SignUpResDTO(
                "success", "User registered successfully."));
    }


    @Override
    public ResponseEntity<?> logout() {
        User user = (User) CommonUtils.getUserFromSecurityContext();

        if (user == null) {
            throw RestException.restThrow("User not found", HttpStatus.UNAUTHORIZED);
        }
        try {
            refreshTokenRepository.deleteByUserId(user.getId());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            throw RestException.restThrow("Error deleting token", HttpStatus.UNAUTHORIZED);
        }

    }
}
