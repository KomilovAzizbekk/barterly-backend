package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CatalogResDTO {

    private Long id;

    private String name;

    private List<CatalogResDTO> catalog;

}
