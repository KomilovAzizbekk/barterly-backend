package uz.mediasolutions.barterlybackend.payload.response;

import java.util.UUID;

public interface UserResDTO {

    UUID getId();

    String getName();

    String getPhoneNumber();

    String getUsername();

}
