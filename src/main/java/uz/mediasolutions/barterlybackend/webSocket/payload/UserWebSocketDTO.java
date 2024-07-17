package uz.mediasolutions.barterlybackend.webSocket.payload;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWebSocketDTO {

    private UUID id;

    private String username;

    private String socketStatus;

}
