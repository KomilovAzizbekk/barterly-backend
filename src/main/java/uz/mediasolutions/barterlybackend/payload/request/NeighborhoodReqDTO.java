package uz.mediasolutions.barterlybackend.payload.request;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NeighborhoodReqDTO {

    private Long regionId;

    private Long cityId;

    private Map<String, String> translations;

}
