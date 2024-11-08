package uz.mediasolutions.barterlybackend.controller.user.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "app/home")
public interface HomeController {

    @GetMapping("/header")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<HeaderResDTO> getHeaderDetails();

    @GetMapping("/search")
    ResponseEntity<Page<ItemDTO>> search(@RequestParam(name = "search") String search,
                                         @RequestParam(name = "categoryId") Long categoryId);

}
