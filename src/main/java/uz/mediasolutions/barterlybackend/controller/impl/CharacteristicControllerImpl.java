package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.abs.CharacteristicController;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;
import uz.mediasolutions.barterlybackend.service.abs.CharacteristicService;

@RestController
@RequiredArgsConstructor
public class CharacteristicControllerImpl implements CharacteristicController {

    private final CharacteristicService characteristicService;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        return characteristicService.getAll(language, page, size);
    }

    @Override
    public ResponseEntity<CharacteristicResDTO> getById(Long id) {
        return characteristicService.getById(id);
    }

    @Override
    public ResponseEntity<?> add(CharacteristicReqDTO dto) {
        return characteristicService.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicReqDTO dto) {
        return characteristicService.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return characteristicService.delete(id);
    }
}
