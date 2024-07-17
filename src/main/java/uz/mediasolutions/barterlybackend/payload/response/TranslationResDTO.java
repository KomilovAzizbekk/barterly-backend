package uz.mediasolutions.barterlybackend.payload.response;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationResDTO {

    @NotBlank
    private JsonNode translation;

}
