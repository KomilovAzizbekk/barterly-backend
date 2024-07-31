package uz.mediasolutions.barterlybackend.controller.admin.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "admin/characteristic-value")
public interface CharacteristicValueController {

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                   @RequestParam(required = false, defaultValue = "") String search,
                                   @RequestParam(required = false, defaultValue = "") Long characteristicId,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> getById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                              @PathVariable Long id);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> add(@RequestBody @Valid CharacteristicValueReqDTO dto);

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody @Valid CharacteristicValueReqDTO dto);

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> delete(@PathVariable Long id);
}
