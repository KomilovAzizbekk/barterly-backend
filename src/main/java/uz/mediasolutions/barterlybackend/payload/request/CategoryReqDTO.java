package uz.mediasolutions.barterlybackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReqDTO {

    private Map<String, String> translations;

    private String imageUrl;

    private Long parentCategoryId;

}
