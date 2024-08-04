package uz.mediasolutions.barterlybackend.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInUserDTO {

    @NotBlank(message = "enter username")
    private String username;

    @NotBlank(message = "enter password")
    private String password;

}
