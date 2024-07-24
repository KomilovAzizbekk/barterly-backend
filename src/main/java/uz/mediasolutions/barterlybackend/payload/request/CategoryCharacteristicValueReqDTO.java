package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCharacteristicValueReqDTO {

    @NotNull
    private Long categoryCharacteristicId;

    @NotNull
    private Map<String, String> translations;

}
