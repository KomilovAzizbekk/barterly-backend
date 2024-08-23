package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.UserProfileService;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> getUserProfileInfo(String lang, UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && !Objects.equals(user.getRole().getName(), RoleEnum.ROLE_USER)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        }
        ProfileDTO profileInfo = userRepository.getUserProfileInfo(lang, id);
        return ResponseEntity.ok(profileInfo);
    }
}
