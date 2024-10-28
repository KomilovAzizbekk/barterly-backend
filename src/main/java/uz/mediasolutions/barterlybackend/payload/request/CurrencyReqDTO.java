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
public class CurrencyReqDTO {

    @NotBlank
    @NotNull
    private String currencyCode;

    @NotBlank
    @NotNull
    private String imageUrl;

    @NotNull
    private Map<String, String> translations;

}
