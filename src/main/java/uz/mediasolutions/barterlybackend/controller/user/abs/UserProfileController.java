package uz.mediasolutions.barterlybackend.controller.user.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO2;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/profile")
public interface UserProfileController {

    @GetMapping("/get/{id}")
    ResponseEntity<ProfileDTO> getUserProfileInfo(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                                  @PathVariable UUID id);

    @GetMapping("/mine")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ProfileDTO2> getMyProfileInfo(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang);

}
