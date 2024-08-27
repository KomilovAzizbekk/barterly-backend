package uz.mediasolutions.barterlybackend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SwapReqDTO {

    @NotNull
    private UUID requesterItemId;

    @NotNull
    private UUID responderItemId;

    private String message;

}
