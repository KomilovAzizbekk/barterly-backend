package uz.mediasolutions.barterlybackend.controller.user.abs;

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
    ResponseEntity<String> addItem(@RequestBody @Valid ItemReqDTO dto);

    @GetMapping("/get/{itemId}")
    ResponseEntity<Item2DTO> getItemById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                         @PathVariable UUID itemId,
                                         @RequestParam(name = "isActive") boolean isActive);

    @PatchMapping("edit/{itemId}")
    ResponseEntity<String> edit(@PathVariable UUID itemId,
                              @RequestBody ItemEditReqDTO dto);

}
