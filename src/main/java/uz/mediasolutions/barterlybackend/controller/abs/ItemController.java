package uz.mediasolutions.barterlybackend.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "items")
public interface ItemController {

//    @GetMapping("/get-all")
//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
//    ResponseEntity<Page<ItemResDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
//                                            @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);



}
