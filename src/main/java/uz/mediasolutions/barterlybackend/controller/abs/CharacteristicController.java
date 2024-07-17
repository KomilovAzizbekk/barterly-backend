package uz.mediasolutions.barterlybackend.controller.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "characteristics")
public interface CharacteristicController {

    @GetMapping("/get-all")
    ResponseEntity<Page<?>> getAll(@RequestHeader(name = "Accept-Language", required = false, defaultValue = "uz") String language,
                                                      @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    ResponseEntity<CharacteristicResDTO> getById(@PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<?> add(@RequestBody @Valid CharacteristicReqDTO dto);

    @PutMapping("/edit/{id}")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody @Valid CharacteristicReqDTO dto);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

}
