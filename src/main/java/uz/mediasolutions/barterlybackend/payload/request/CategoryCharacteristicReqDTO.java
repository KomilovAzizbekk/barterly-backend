package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCharacteristicReqDTO {

    @NotNull(message = "choose category")
    private Long categoryId;

    @NotNull(message = "choose whether appears in title")
    private Boolean title;

    private Long parentId;

    @NotNull(message = "enter translations")
    private Map<String, String> translations;

}
