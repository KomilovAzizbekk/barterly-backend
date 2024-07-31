package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface CategoryDTO2 {

    Long getId();

    String getImageUrl();

    Map<String, String> getNames();

    Long getParentId();

    String getParentName();

}
