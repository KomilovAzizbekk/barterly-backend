package uz.mediasolutions.barterlybackend.service.common.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Favorite;
import uz.mediasolutions.barterlybackend.entity.Item;
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
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.secret.JwtService;
import uz.mediasolutions.barterlybackend.service.common.abs.AuthService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final GeocodingService geocodingService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ItemRepository itemRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public ResponseEntity<TokenDTO> signInAdmin(SignInAdminDTO adminDTO) {
        Optional<User> optionalUser = userRepository.findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(adminDTO.getUsername());

        if (optionalUser.isEmpty()) {
            throw RestException.restThrow("Admin not found", HttpStatus.UNAUTHORIZED);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(adminDTO.getPassword(), user.getPassword())) {
            throw RestException.restThrow("Password is incorrect", HttpStatus.UNAUTHORIZED);
        } else if (user.getAuthorities().contains(roleRepository.findByName(RoleEnum.ROLE_USER))) {
            throw RestException.restThrow("Role is incorrect", HttpStatus.UNAUTHORIZED);
        }

        TokenDTO tokenDTO = getTokenDTO(user);

        return ResponseEntity.ok(tokenDTO);
    }


    @Override
    public ResponseEntity<?> signInUser(SignInUserDTO dto) {
        if (dto.getOtp() == null) {
            //todo otp should be sent and save to database.
            return ResponseEntity.ok("Otp sent to user: 0000");
        } else {
            //todo database'dan ushbu raqamga mos otp tekshirib kelinadi
            if (Objects.equals(dto.getOtp(), "0000")) {
                Optional<User> optionalUser = userRepository.findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(dto.getPhoneNumber());

                //If user is not existed return "New user" msg
                if (optionalUser.isEmpty())
                    return ResponseEntity.ok("New user");
                else {
                    //else return generated TokenDTO
                    return ResponseEntity.ok(getTokenDTO(optionalUser.get()));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect OTP");
            }
        }
    }


    @Override
    public ResponseEntity<SignUpResDTO> signUpUser(SignUpUserDTO dto, HttpSession session) {

        if (!dto.getPhoneNumber().matches(Rest.PHONE_NUMBER_REGEX)) {
            throw RestException.restThrow("Phone number is not valid", HttpStatus.BAD_REQUEST);
        }

//        Optional<User> optional = userRepository.findByPhoneNumber(dto.getPhoneNumber());
//        if (optional.isPresent()) {
//            throw RestException.restThrow("User already existed", HttpStatus.BAD_REQUEST);
//        }

        System.out.println(geocodingService.getCountryCityAndDistrict(dto.getLat(), dto.getLon()));

        User user = User.builder()
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .username(dto.getUsername())
                .role(roleRepository.findByName(RoleEnum.ROLE_USER))
                .level(1)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        User savedUser = userRepository.save(user);

        Set<UUID> likedItems = (Set<UUID>) session.getAttribute("likedItems");
        if (likedItems != null) {
            for (UUID likedItem : likedItems) {
                Item item = itemRepository.findById(likedItem).orElse(null);
                if (item != null) {
                    Favorite favorite = Favorite.builder()
                            .item(item)
                            .user(savedUser)
                            .build();
                    favoriteRepository.save(favorite);
                }
            }
        }

//        otpService.generateOtp(dto.getPhoneNumber());

        return ResponseEntity.ok(new SignUpResDTO(
                "success", "User registered successfully."));
    }


    @Override
    public ResponseEntity<?> logout() {
        User user = (User) CommonUtils.getUserFromSecurityContext();


        if (user == null) {
            throw RestException.restThrow("User not found", HttpStatus.NOT_FOUND);
        }
        System.out.println(user.getName());
        try {
            RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
            refreshTokenRepository.deleteById(token.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }

    private TokenDTO getTokenDTO(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        //Saving refresh token to database
        RefreshToken token;
        if (refreshTokenRepository.existsByUserId(user.getId())) {
            token = refreshTokenRepository.findByUserId(user.getId());
            token.setExpireDate(jwtService.extractExpiration(refreshToken));
            token.setToken(passwordEncoder.encode(refreshToken));
        } else {
            token = RefreshToken.builder()
                    .token(passwordEncoder.encode(refreshToken))
                    .user(user)
                    .expireDate(jwtService.extractExpiration(refreshToken))
                    .build();
        }
        refreshTokenRepository.save(token);

        return new TokenDTO("Bearer", accessToken, refreshToken);
    }

    public UUID getAuthenticatedUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " so'zini olib tashlaymiz
            try {
               return jwtService.extractUserId(token);
            } catch (Exception e) {
                // Token noto'g'ri yoki muddati o'tgan bo'lishi mumkin
                return null;
            }
        }
        return null;
    }
}
