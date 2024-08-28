package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCharacteristicReqDTO2 {

    @NotNull
    private Long categoryCharacteristicId;

    @NotNull
    private Long categoryCharacteristicValueId;

}
