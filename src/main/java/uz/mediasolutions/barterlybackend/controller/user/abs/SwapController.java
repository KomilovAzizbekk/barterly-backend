package uz.mediasolutions.barterlybackend.controller.user.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/swaps")
public interface SwapController {

    @GetMapping("/all/{userId}")
    ResponseEntity<Page<SwapDTO>> getAllByUserId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                                 @PathVariable UUID userId,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/mine")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<Page<SwapDTO>> getAllMine(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<String> create(@RequestBody @Valid SwapReqDTO dto);

    @PutMapping("/accept/{swapId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<String> accept(@PathVariable(name = "swapId") UUID swapId,
                                  @RequestParam(name = "accept", defaultValue = "true") boolean accept);

}
