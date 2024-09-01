package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NeighborhoodReqDTO {

    @NotNull
    private Long regionId;

    @NotNull
    private Long cityId;

    @NotNull
    private Map<String, String> translations;

}
