package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CharacteristicTypeReqDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private Map<String, String> translations;

}
