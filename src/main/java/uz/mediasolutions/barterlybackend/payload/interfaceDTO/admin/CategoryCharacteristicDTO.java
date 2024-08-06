package uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin;

public interface CategoryCharacteristicDTO {

    Long getId();

    String getName();

    Long getCategoryId();

    String getCategoryName();

    Long getParentCharacteristicId();

    String getParentCharacteristicName();
}
