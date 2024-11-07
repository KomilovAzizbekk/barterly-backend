package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemResDTO {

    private UUID id;

    private UUID userId;

    private String title;

    private String description;

    private List<String> imageUrls;

    private List<CharacteristicTypeResDTO2> characteristicTypes;

}
