package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemEditReqDTO {

    @Valid
    private List<ItemCharacteristicEditDTO> characteristics;

    private List<String> imageUrls;

    private String description;

    private Boolean isActive;

    private Boolean isPremium;

    private Boolean isTemporary;

}
