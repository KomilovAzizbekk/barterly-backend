package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemCharacteristicReqDTO {

    @NotNull
    private Long characteristicId;

    private Long characteristicValueId;

    private String value;

}
