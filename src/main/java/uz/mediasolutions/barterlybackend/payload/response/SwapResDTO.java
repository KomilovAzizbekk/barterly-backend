package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SwapResDTO {

    private UUID id;

    private String username;

    private String title1;

    private String title2;

    private String createdAt;

}
