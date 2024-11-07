package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemCharacteristicEditDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long characteristicId;

    private Long characteristicValueId;

    private String value;

}
