package uz.mediasolutions.barterlybackend.service.user.abs;

import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;

import java.util.UUID;

public interface UserProfileService {

    ProfileDTO getUserProfileInfo(String lang, UUID id);
}
