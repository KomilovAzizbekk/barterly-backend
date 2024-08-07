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
public class SignUpUserDTO {

    @NotBlank(message = "enter name")
    private String name;

    @NotBlank(message = "enter phone number")
    private String phoneNumber;

    @NotBlank(message = "enter username")
    private String username;

//    @NotBlank(message = "enter user type")
//    private Long userTypeId;  //Business or individual

    private Double lon;

    private Double lat;

}
