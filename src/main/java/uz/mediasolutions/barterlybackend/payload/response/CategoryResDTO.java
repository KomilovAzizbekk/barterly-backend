package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryDTO;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResDTO {

    private Long id;

    private String imageUrl;

    private Map<String, String> translations;

}
