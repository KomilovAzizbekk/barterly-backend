package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

public interface CharacteristicDTO {

    Long getId();

    boolean isRequired();

    String getName();

    Long getCategoryId();

    String getCategoryName();

    Long getCategoryCharacteristicId();

    String getCategoryCharacteristicName();


}
