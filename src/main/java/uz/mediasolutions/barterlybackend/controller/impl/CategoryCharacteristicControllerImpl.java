package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.abs.CategoryCharacteristicController;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.service.abs.CategoryCharacteristicService;

@RestController
@RequiredArgsConstructor
public class CategoryCharacteristicControllerImpl implements CategoryCharacteristicController {

    private CategoryCharacteristicService service;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        return service.getAll(language, page, size);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        return service.getById(id);
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
