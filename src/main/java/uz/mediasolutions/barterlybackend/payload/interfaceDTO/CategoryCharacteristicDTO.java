package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

public interface CategoryCharacteristicDTO {

    Long getId();

    String getName();

    Long getCategoryId();

    String getCategoryName();

    Long getParentCharacteristicId();

    String getParentCharacteristicName();
}
