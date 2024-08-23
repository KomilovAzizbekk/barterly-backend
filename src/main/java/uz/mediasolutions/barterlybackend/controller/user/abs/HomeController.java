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
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "app/home")
public interface HomeController {

    @GetMapping("/header")
    ResponseEntity<HeaderResDTO> getHeaderDetails(HttpServletRequest request,
                                                  HttpSession session);

    @GetMapping("/items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))})
    })
    ResponseEntity<Page<?>> getItems(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                     HttpServletRequest request,
                                     HttpSession session);

//    @GetMapping("/search")
//    ResponseEntity<Page<?>> search(@RequestParam(name = "q") String query)

}
