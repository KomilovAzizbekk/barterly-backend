package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

import java.util.Map;

public interface CharacteristicDTO2 {

    Long getId();

    boolean isRequired();

    Map<String, String> getNames();

    Long getCategoryId();

    String getCategoryName();

}
