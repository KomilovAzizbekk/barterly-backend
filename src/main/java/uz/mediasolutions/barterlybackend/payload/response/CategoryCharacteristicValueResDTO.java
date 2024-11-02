package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCharacteristicValueResDTO {

    private Long id;

    private Map<String, String> names;

    private Long categoryCharacteristicId;

    private String categoryCharacteristicName;

}
