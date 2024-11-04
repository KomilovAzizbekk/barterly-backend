package uz.mediasolutions.barterlybackend.controller.user.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "app/home")
public interface HomeController {

    @GetMapping("/header")
    ResponseEntity<HeaderResDTO> getHeaderDetails();

    @GetMapping("/items")
    ResponseEntity<Page<ItemDTO>> getItems(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/search")
    ResponseEntity<Page<ItemDTO>> search(@RequestParam(name = "search") String search,
                                         @RequestParam(name = "categoryId") Long categoryId);

}
