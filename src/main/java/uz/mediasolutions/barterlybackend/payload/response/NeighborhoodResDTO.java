package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NeighborhoodResDTO {

    private Long id;

    private Map<String, String> names;

    private Long regionId;

    private String regionName;

    private Long cityId;

    private String cityName;

}
