package uz.mediasolutions.barterlybackend.controller.user.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "favorites")
public interface FavoriteController {

    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))})
    })
    ResponseEntity<Page<?>> getByUserId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                        @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                        @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                        HttpSession session,
                                        HttpServletRequest request);

    @PostMapping("/add/{itemId}")
    ResponseEntity<?> addFavorite(@PathVariable UUID itemId,
                                  @RequestParam(name = "like", defaultValue = "false") boolean like,
                                  HttpSession session,
                                  HttpServletRequest request);


}
