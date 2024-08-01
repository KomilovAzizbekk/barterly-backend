package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface CityDTO2 {

    Long getId();

    Map<String, String> getNames();

    Long getRegionId();

    String getRegionName();
}
