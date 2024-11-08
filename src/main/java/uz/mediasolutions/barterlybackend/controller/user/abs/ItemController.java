package uz.mediasolutions.barterlybackend.controller.user.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.ItemResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/items")
public interface ItemController {

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<String> addItem(@RequestBody @Valid ItemReqDTO dto);

    @GetMapping("/get/items")
    ResponseEntity<Page<ItemDTO>> getItems(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                           @RequestParam(defaultValue = "") Boolean premium);

    @GetMapping("/get/{itemId}")
    ResponseEntity<ItemResDTO> getItemById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                           @PathVariable UUID itemId);

    @GetMapping("/get/by-user/{userId}")
    ResponseEntity<Page<ItemDTO>> getItemsByUserId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                                   @PathVariable UUID userId,
                                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/my-items")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<Page<ItemDTO>> getMyItems(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/my-item/stats")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> getMyItemStatsFilter(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang);

    @PatchMapping("/edit/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<String> edit(@PathVariable UUID itemId,
                                @RequestBody ItemEditReqDTO dto);


}
