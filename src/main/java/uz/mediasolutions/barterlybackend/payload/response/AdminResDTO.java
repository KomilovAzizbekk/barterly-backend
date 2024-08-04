package uz.mediasolutions.barterlybackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResDTO {

    private UUID id;

    private String username;

    private String role;

}
