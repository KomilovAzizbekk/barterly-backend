package uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin;

public interface CategoryCharacteristicDTO {

    Long getId();

    String getName();

    Long getCategoryId();

    boolean isTitle();

    String getCategoryName();

    Long getParentCharacteristicId();

    String getParentCharacteristicName();
}
