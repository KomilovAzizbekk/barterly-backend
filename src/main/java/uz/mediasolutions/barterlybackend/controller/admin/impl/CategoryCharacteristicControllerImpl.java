package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CategoryCharacteristicController;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryCharacteristicService;

@RestController
@RequiredArgsConstructor
public class CategoryCharacteristicControllerImpl implements CategoryCharacteristicController {

    private final CategoryCharacteristicService service;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long categoryId,
                                          Long parentCharacteristicId, int page, int size) {
        return service.getAll(lang, search, categoryId, parentCharacteristicId, page, size);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return service.getById(lang, id);
    }

    @Override
    public ResponseEntity<?> add(CategoryCharacteristicReqDTO dto) {
        return service.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CategoryCharacteristicReqDTO dto) {
        return service.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return service.delete(id);
    }
}
