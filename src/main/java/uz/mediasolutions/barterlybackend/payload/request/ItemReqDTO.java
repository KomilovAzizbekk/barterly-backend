package uz.mediasolutions.barterlybackend.payload.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemReqDTO {

    private Long categoryId;

    private Long categoryCharacteristicValueId;

    private List<ItemCharacteristicReqDTO> characteristics;

    private List<String> imageUrls;

    private String description;

    private Long value;

}
