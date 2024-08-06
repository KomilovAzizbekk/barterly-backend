package uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin;

public interface CharacteristicDTO {

    Long getId();

    boolean isRequired();

    String getName();

    Long getCategoryId();

    String getCategoryName();

}
