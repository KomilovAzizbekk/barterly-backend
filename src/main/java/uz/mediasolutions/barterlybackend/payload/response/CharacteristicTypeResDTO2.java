package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicResDTO2;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CharacteristicTypeResDTO2 {

    private Long id;

    private String name;

    private List<CharacteristicResDTO2> characteristics;

}
