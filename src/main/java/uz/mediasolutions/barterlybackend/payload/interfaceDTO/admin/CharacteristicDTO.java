package uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin;

public interface CharacteristicDTO {

    Long getId();

    boolean isRequired();

    boolean isTitle();

    boolean isFilter();

    String getName();

    Long getCategoryId();

    String getCategoryName();

}
