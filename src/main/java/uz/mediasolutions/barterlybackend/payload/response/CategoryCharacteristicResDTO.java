package uz.mediasolutions.barterlybackend.payload.response;

import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCharacteristicResDTO {

    private Long id;

    private Map<String, String> names;

    private boolean title;

    private Long categoryId;

    private String categoryName;

    private Long parentCharacteristicId;

    private String parentCharacteristicName;

}
