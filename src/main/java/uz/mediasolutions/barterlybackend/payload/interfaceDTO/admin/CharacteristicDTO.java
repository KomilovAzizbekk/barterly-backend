package uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin;

public interface CharacteristicDTO {

    Long getId();

    boolean isTitle();

    boolean isFilter();

    String getName();

    Long getCharacteristicTypeId();

    String getCharacteristicTypeName();

    Long getCategoryId();

    String getCategoryName();

}
