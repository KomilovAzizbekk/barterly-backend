package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeaderResDTO {

    private Integer favorites;

    private Integer swaps;

    private Integer profile;

}
