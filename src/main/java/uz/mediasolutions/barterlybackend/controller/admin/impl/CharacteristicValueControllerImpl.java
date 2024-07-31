package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CharacteristicValueController;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.service.abs.CharacteristicValueService;

@RestController
@RequiredArgsConstructor
public class CharacteristicValueControllerImpl implements CharacteristicValueController {

    private final CharacteristicValueService valueService;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long characteristicId, int page, int size) {
        return valueService.getAll(lang, search, characteristicId, page, size);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return valueService.getById(lang, id);
    }

    @Override
    public ResponseEntity<?> add(CharacteristicValueReqDTO dto) {
        return valueService.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicValueReqDTO dto) {
        return valueService.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return valueService.delete(id);
    }
}
