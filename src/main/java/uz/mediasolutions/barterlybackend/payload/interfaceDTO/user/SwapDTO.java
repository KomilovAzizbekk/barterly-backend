package uz.mediasolutions.barterlybackend.payload.interfaceDTO.user;

import java.util.UUID;

public interface SwapDTO {

    UUID getId();

    String getCreatedAt();

    String getUsername();

    String getTitle1();

    String getTitle2();

    String getStatus();

}
