package uz.mediasolutions.barterlybackend.payload.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleResDTO {

    private UUID id;

    private String name;

}
