package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegionReqDTO {

    @NotBlank
    private String imageUrl;

    @NotNull
    private Long currencyId;

    @NotNull
    private Map<String, String> translations;

}
