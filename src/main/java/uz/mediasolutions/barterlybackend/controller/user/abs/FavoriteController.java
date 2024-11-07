package uz.mediasolutions.barterlybackend.controller.user.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/favorites")
public interface FavoriteController {

    @GetMapping("/get")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<Page<ItemDTO>> getByUserId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                              @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping("/add/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<String> addFavorite(@PathVariable UUID itemId,
                                       @RequestParam(name = "like", defaultValue = "true") boolean like);


}
