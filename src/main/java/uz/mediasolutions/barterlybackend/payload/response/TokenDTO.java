package uz.mediasolutions.barterlybackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {

    private String tokenType;

    private String accessToken;

    private String refreshToken;

}
