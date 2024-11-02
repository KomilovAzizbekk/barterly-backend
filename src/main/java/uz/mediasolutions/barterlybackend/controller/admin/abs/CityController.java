package uz.mediasolutions.barterlybackend.controller.admin.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CityDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CityDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CityReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "admin/cities")
public interface CityController {

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class))})
    })
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                   @RequestParam(required = false) String search,
                                   @RequestParam(required = false) Long regionId,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);


    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> getById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                              @PathVariable Long id);


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> add(@RequestBody @Valid CityReqDTO dto);


    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody CityReqDTO dto);


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> delete(@PathVariable Long id);

}
