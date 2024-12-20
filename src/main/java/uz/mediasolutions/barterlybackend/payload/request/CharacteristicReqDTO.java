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

    @NotNull(message = "choose category")
    private Long categoryId;

    @NotNull(message = "choose characteristic type")
    private Long characteristicTypeId;

    @NotNull
    private Boolean filter;

    @NotNull
    private Boolean title;

    @NotNull(message = "enter translations")
    private Map<String, String> translations;

}
