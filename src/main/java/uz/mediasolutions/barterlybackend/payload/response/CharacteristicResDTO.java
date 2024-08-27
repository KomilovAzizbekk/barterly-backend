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

    private boolean required;

    private boolean filter;

    private boolean title;

    private Map<String, String> translations;
}
