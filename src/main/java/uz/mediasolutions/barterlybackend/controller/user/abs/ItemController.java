package uz.mediasolutions.barterlybackend.controller.user.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.Item2DTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/items")
public interface ItemController {

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> addItem(@RequestBody @Valid ItemReqDTO dto);

    @GetMapping("/get/{itemId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Item2DTO.class))})
    })
    ResponseEntity<?> getItemById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                  @PathVariable UUID itemId,
                                  @RequestParam(name = "isActive") boolean isActive);

    @PatchMapping("edit/{itemId}")
    ResponseEntity<?> edit(@PathVariable UUID itemId,
                           @RequestBody ItemEditReqDTO dto);

}
