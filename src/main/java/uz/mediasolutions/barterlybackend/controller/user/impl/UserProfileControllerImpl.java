package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.UserProfileController;
import uz.mediasolutions.barterlybackend.service.user.abs.UserProfileService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserProfileControllerImpl implements UserProfileController {

    private final UserProfileService service;

    @Override
    public ResponseEntity<?> getUserProfileInfo(String lang, UUID id) {
        return service.getUserProfileInfo(lang, id);
    }
}
