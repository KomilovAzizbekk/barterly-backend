package uz.mediasolutions.barterlybackend.controller.abs;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@RequestMapping(Rest.BASE_PATH + "characteristic-value")
public interface CharacteristicValueController {

    @GetMapping("/get-all/{characteristicId}")
    ResponseEntity<Page<?>> getAllByCharacteristicId(@PathVariable Long characteristicId,
                                                     @RequestHeader(name = "Accept-Language", required = false, defaultValue = "uz") String language,
                                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/get/{id}")
    ResponseEntity<CharacteristicValueResDTO> getById(@PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<?> add(@RequestBody @Valid CharacteristicValueReqDTO dto);

    @PutMapping("/edit/{id}")
    ResponseEntity<?> edit(@PathVariable Long id,
                           @RequestBody @Valid CharacteristicValueReqDTO dto);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);
}
