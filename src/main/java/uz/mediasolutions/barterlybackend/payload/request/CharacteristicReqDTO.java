package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CharacteristicReqDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private Long categoryCharacteristicId;

    @NotNull
    private boolean required;

    @NotNull
    private Map<String, String> translations;

}
