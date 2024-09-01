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

    @NotNull(message = "choose category characteristic")
    private Long categoryCharacteristicId;

    @NotNull(message = "enter translations")
    private Map<String, String> translations;

}
