package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.abs.CategoryCharacteristicValueController;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.service.abs.CategoryCharacteristicValueService;

@RestController
@RequiredArgsConstructor
public class CategoryCharacteristicValueControllerImpl implements CategoryCharacteristicValueController {

    private final CategoryCharacteristicValueService service;

    @Override
    public ResponseEntity<Page<?>> getAllByCategoryCharacteristicId(Long categoryCharacteristicId, String language, int page, int size) {
        return service.getAllByCategoryCharacteristicId(categoryCharacteristicId, language, page, size);
    }

    @Override
    public ResponseEntity<CharacteristicValueResDTO> getById(Long id) {
        return service.getById(id);
    }

    @Override
    public ResponseEntity<?> add(CategoryCharacteristicValueReqDTO dto) {
        return service.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CategoryCharacteristicValueReqDTO dto) {
        return service.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return service.delete(id);
    }
}
