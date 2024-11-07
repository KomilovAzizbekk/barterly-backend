package uz.mediasolutions.barterlybackend.payload.interfaceDTO.user;

import java.util.UUID;

public interface ProfileDTO2 {

    UUID getId();

    String getName();

    String getUsername();

    String getPhoneNumber();

    int getLevel();

    int getItems();

    int getPoints();

    int getMaxPoints();

    int getOffers();

    int getSwaps();

    String getCreatedAt();

    String getCity();

    String getNeighborhood();

}
