package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CategoryCharacteristicValueController;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryCharacteristicValueService;

@RestController
@RequiredArgsConstructor
public class CategoryCharacteristicValueControllerImpl implements CategoryCharacteristicValueController {

    private final CategoryCharacteristicValueService service;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, Long categoryCharacteristicId, String search, int page, int size) {
        return service.getAll(lang, categoryCharacteristicId, search, page, size);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return service.getById(lang, id);
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
