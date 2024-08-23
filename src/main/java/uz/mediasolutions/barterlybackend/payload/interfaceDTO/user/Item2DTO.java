package uz.mediasolutions.barterlybackend.payload.interfaceDTO.user;

import java.util.List;
import java.util.UUID;

public interface Item2DTO {

    UUID getId();

    UUID getUserId();

    String getTitle();

    List<String> getImageUrls();

    String getDescription();

    Object getCharacteristics();

}
