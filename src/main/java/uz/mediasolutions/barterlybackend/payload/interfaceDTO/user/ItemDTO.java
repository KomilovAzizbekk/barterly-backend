package uz.mediasolutions.barterlybackend.payload.interfaceDTO.user;

import java.util.UUID;

public interface ItemDTO {

    UUID getId();

    String getTitle();

    String getUsername();

    String getCategory();

    Object getImageUrls();

    boolean getLiked();

}
