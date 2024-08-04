package uz.mediasolutions.barterlybackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminReqDTO {

    private String username;

    private String password;

    private UUID roleId;

}
