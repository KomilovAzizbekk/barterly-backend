package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "enter translations")
    private Map<String, String> translations;

    @NotBlank(message = "enter image url")
    @NotNull
    private String imageUrl;

    private Long parentCategoryId;

}
