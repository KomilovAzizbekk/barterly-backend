package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemReqDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private List<ItemCharacteristicReqDTO> characteristics;

    @NotNull
    private List<String> imageUrls;

    @NotBlank
    private String description;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Boolean isPremium;

    @NotNull
    private Boolean isTemporary;

}
