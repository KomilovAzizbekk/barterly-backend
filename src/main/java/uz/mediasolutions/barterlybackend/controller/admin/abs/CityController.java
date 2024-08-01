package uz.mediasolutions.barterlybackend.controller.admin.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.CityReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "admin/cities")
public interface CityController {

    @GetMapping("/get-all")
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                                 @RequestParam(required = false, defaultValue = "") String search,
                                 @RequestParam(required = false, defaultValue = "") Long regionId,
                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    ResponseEntity<?> get(@RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
                          @PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<?> add(@RequestBody @Valid CityReqDTO dto);

    @PutMapping("/edit/{id}")
    ResponseEntity<?> edit(@RequestBody @Valid CityReqDTO dto, @PathVariable Long id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

}
