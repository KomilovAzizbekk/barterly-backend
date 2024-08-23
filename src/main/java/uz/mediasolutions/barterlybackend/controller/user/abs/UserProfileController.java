package uz.mediasolutions.barterlybackend.controller.user.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/profile")
public interface UserProfileController {

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDTO.class))})
    })
    ResponseEntity<?> getUserProfileInfo(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                         @PathVariable UUID id);

}
