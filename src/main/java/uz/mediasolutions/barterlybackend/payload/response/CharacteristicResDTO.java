package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CharacteristicResDTO {

    private Long id;

    private boolean filter;

    private boolean title;

    private Map<String, String> names;

    private Long categoryId;

    private String categoryName;

    private Long characteristicTypeId;

    private String characteristicTypeName;
}
