package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResDTO {

    private Long id;

    private String imageUrl;

    private Map<String, String> names;

    private Long parentId;

    private String parentName;

}
