package uz.mediasolutions.barterlybackend.payload.request;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCharacteristicReqDTO {

    private Long categoryId;

    private Long parentId;

    private Map<String, String> translations;

}
