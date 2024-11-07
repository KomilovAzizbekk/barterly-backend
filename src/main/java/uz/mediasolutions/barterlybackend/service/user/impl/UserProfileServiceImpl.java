package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO2;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.UserProfileService;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileDTO getUserProfileInfo(String lang, UUID id) {
        // User'ni ID bo'yicha izlaymiz agar yo'q bo'lsa 404 qaytaramiz
        User user = userRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("User not Found", HttpStatus.NOT_FOUND)
        );

        // Agar USER'dan boshqa rollik userni ma'lumotlarini ko'rmoqchi bo'lsa 404 beramiz
        if (!Objects.equals(user.getRole().getName(), RoleEnum.ROLE_USER)) {
            throw RestException.restThrow("Bad request", HttpStatus.NOT_FOUND);
        }
        return userRepository.getUserProfileInfo(lang, id);
    }

    @Override
    public ProfileDTO2 getMyProfileInfo(String lang) {
        // Security Context'dan User'ni olamiz
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Agar User null ga teng bo'lsa 401 qaytarib yuboramiz
        if (user == null) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        return userRepository.getMyProfileInfo(lang, user.getId());
    }
}
