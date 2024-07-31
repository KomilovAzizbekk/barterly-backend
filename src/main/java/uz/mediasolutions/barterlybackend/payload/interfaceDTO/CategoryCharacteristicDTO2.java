package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface CategoryCharacteristicDTO2 {

    Long getId();

    Map<String, String> getNames();

    Long getCategoryId();

    String getCategoryName();

    Long getParentCharacteristicId();

    String getParentCharacteristicName();
}
