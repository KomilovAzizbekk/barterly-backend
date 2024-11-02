package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CityResDTO {

    private Long id;

    private Map<String, String> names;

    private Long regionId;

    private String regionName;

}
