package uz.mediasolutions.barterlybackend.controller.admin.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.entity.Neighborhood;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "admin/neighborhood")
public interface NeighborhoodController {

    @GetMapping("/get-all")
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language") String lang,
                                   @RequestParam(required = false, defaultValue = "") String search,
                                   @RequestParam(required = false, defaultValue = "") Long regionId,
                                   @RequestParam(required = false, defaultValue = "") Long cityId,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                   @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    ResponseEntity<?> getById(@RequestHeader(name = "Accept-Language") String lang,
                              @PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<?> add(@RequestBody @Valid NeighborhoodReqDTO dto);

    @PutMapping("/edit/{id}")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody @Valid NeighborhoodReqDTO dto);

    @PutMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

}
