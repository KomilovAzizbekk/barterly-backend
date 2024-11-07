package uz.mediasolutions.barterlybackend.payload.interfaceDTO.user;

import java.util.UUID;

public interface ItemDTO {

    UUID getId();

    String getTitle();

    String getUsername();

    String getCategory();

    String getUpdatedTime();

    Boolean getLiked();

    Object getImageUrls();

    boolean getTemporary();

    boolean getPremium();

}
