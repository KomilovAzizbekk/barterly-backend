package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserProfileService {

    ResponseEntity<?> getUserProfileInfo(String lang, UUID id);
}
