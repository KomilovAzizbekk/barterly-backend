package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface RegionDTO2 {

    Long getId();

    Map<String, String> getNames();

    String getImageUrl();

    String getCurrencyCode();

    Long getCurrencyId();

}