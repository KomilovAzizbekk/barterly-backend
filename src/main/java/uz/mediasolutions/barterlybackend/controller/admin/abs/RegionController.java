package uz.mediasolutions.barterlybackend.controller.admin.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "admin/region")
public interface RegionController {

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                   @RequestParam(required = false, defaultValue = "") String search,
                                   @RequestParam(required = false, defaultValue = "") Long currencyId,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> getById(@PathVariable Long id);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> add(@RequestBody @Valid RegionReqDTO dto);

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody @Valid RegionReqDTO dto);

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> delete(@PathVariable Long id);

}
