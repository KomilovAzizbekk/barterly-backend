package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemReqDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private Long categoryCharacteristicValueId;

    @NotNull
    private List<ItemCharacteristicReqDTO> characteristics;

    @NotNull
    private List<String> imageUrls;

    @NotBlank
    private String description;

}
