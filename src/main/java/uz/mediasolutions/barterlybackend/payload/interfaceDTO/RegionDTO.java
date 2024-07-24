package uz.mediasolutions.barterlybackend.payload.interfaceDTO;

public interface RegionDTO {

    Long getId();

    String getName();

    String getImageUrl();

    CurrencyDTO getCurrency();

    interface CurrencyDTO {
        Long id();
        String name();
        String currency_code();
    }

}
