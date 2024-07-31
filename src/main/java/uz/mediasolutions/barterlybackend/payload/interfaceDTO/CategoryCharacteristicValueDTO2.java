package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface CategoryCharacteristicValueDTO2 {

    Long getId();

    Map<String, String> getNames();

    Long getCategoryCharacteristicId();

    String getCategoryCharacteristicName();

}
