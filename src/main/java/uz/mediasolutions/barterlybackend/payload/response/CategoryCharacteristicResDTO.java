package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCharacteristicResDTO {

    private Long id;

    private Map<String, String> translations;

}
