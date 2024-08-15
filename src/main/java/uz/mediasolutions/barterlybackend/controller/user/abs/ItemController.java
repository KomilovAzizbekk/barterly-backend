package uz.mediasolutions.barterlybackend.controller.user.abs;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/items")
public interface ItemController {

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> addItem(@Valid @RequestBody ItemReqDTO dto);

    @GetMapping("/{itemId}")
    ResponseEntity<?> getItemById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                  @PathVariable UUID itemId);

}
