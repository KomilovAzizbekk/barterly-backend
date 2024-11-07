package uz.mediasolutions.barterlybackend.service.user.abs;

import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO2;

import java.util.UUID;

public interface UserProfileService {

    ProfileDTO getUserProfileInfo(String lang, UUID id);

    ProfileDTO2 getMyProfileInfo(String lang);
}
