package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface NeighborhoodDTO2 {

    Long getId();

    Map<String, String> getNames();

    Long getRegionId();

    String getRegionName();

    Long getCityId();

    String getCityName();

}
