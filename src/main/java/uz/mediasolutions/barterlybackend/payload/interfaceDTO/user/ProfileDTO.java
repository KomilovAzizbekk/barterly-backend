package uz.mediasolutions.barterlybackend.payload.interfaceDTO.user;

import java.util.UUID;

public interface ProfileDTO {

    UUID getId();

    String getName();

    String getUsername();

    Integer getLevel();

    Integer getSwaps();

    Integer getOffers();

    String getCreatedAt();

    String getCity();

    String getNeighborhood();

    String getPhoneNumber();

}
