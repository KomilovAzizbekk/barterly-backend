package uz.mediasolutions.barterlybackend.controller.admin.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicTypeDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicTypeReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO3;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;

@RequestMapping(Rest.BASE_PATH + "admin/characteristic-type")
public interface CharacteristicTypeController {

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ResponseEntity<Page<CharacteristicTypeDTO>> getAll(@RequestHeader(value = "Accept-Language", defaultValue = "uz") String lang,
                                                       @RequestParam(required = false) String search,
                                                       @RequestParam(required = false) Long categoryId,
                                                       @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ResponseEntity<CharacteristicTypeResDTO> getById(@RequestHeader(value = "Accept-Language", defaultValue = "uz") String lang,
                                                     @PathVariable Long id);

    @GetMapping("/get-by-category/{categoryId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ResponseEntity<List<CharacteristicTypeResDTO3>> getByCategoryId(@RequestHeader(value = "Accept-Language", defaultValue = "uz") String lang,
                                                                    @PathVariable Long categoryId);

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ResponseEntity<String> add(@RequestBody @Valid CharacteristicTypeReqDTO dto);

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ResponseEntity<String> edit(@PathVariable Long id,
                                @RequestBody CharacteristicTypeReqDTO dto);

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ResponseEntity<String> delete(@PathVariable Long id);


}
