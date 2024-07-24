package uz.mediasolutions.barterlybackend.payload.request;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegionReqDTO {

    private String imageUrl;

    private Long currencyId;

    private Map<String, String> translations;

}
