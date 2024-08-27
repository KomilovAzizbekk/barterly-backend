package uz.mediasolutions.barterlybackend.controller.user.abs;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.UUID;

@RequestMapping(Rest.BASE_PATH + "app/swaps")
public interface SwapController {

    @GetMapping("/all/{userId}")
    ResponseEntity<?> getAllByUserId(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                     @PathVariable UUID userId,
                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody @Valid SwapReqDTO dto);

    @PostMapping("/accept/{swapId}")
    ResponseEntity<?> accept(@PathVariable(name = "swapId") UUID swapId,
                             @RequestParam(name = "accept") boolean accept);

}
