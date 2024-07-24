package uz.mediasolutions.barterlybackend.controller.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "region")
public interface RegionController {

    @GetMapping("/get-all")
    ResponseEntity<Page<?>> getAll(@RequestHeader("Accept-Language") String language,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    ResponseEntity<?> getById(@PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<?> add(@RequestBody @Valid RegionReqDTO dto);

    @PutMapping("/edit/{id}")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody @Valid RegionReqDTO dto);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

}
