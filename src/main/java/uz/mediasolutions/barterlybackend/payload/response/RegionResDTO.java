package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegionResDTO {

    private Long id;

    private Map<String, String> translations;

    private String imageUrl;

    private CurrencyResDTO currency;

}
