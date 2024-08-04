package uz.mediasolutions.barterlybackend.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;

    private String name;

    private String phoneNumber;

    private String password;

    private String username;

}
