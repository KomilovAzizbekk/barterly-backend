package uz.mediasolutions.barterlybackend.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CharacteristicResDTO2 {

    private Long id;

    private String name;

    private String value;

}
