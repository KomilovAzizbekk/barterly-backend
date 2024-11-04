package uz.mediasolutions.barterlybackend.payload.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemEditReqDTO {

    private List<ItemCharacteristicReqDTO> characteristics;

    private List<String> imageUrls;

    private String description;

    private Boolean isActive;

    private Boolean isPremium;

    private Boolean isTemporary;

}
