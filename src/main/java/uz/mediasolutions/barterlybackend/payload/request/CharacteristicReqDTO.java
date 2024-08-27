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
    private boolean required;

    @NotNull
    private boolean filter;

    @NotNull
    private boolean title;

    @NotNull
    private Map<String, String> translations;

}
