package uz.mediasolutions.barterlybackend.payload.response;

import java.util.UUID;

public interface AdminResDTO {

    UUID getId();

    String getUsername();

    String getRole();

    UUID getRoleId();

}
