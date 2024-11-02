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
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.NeighborhoodDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.NeighborhoodDTO2;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "admin/neighborhood")
public interface NeighborhoodController {


    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NeighborhoodDTO.class))})
    })
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                   @RequestParam(required = false) String search,
                                   @RequestParam(required = false) Long regionId,
                                   @RequestParam(required = false) Long cityId,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);


    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> getById(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                              @PathVariable Long id);


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> add(@RequestBody @Valid NeighborhoodReqDTO dto);


    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody NeighborhoodReqDTO dto);


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ResponseEntity<?> delete(@PathVariable Long id);

}
