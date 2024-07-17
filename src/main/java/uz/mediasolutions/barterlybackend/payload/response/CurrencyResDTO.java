package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyResDTO {

    private Long id;

    private String currencyCode;

    private String imageUrl;

    private Map<String, String> translations;

}
