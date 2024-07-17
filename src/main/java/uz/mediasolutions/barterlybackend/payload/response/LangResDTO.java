package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LangResDTO {

    private Long id;

    private String language;

    private String name;

}
