package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationReqDTO {

    @NotBlank
    private String language;

    @NotBlank
    private String name;

}
