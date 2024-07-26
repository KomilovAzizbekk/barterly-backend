package uz.mediasolutions.barterlybackend.mapper.abs;

import lombok.*;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;

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
