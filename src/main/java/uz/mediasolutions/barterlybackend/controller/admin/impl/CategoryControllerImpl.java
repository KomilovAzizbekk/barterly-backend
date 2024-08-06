package uz.mediasolutions.barterlybackend.controller.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.admin.abs.CategoryController;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long parentId, int page, int size) {
        return categoryService.getAll(lang, search, parentId, page, size);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        return categoryService.getById(lang, id);
    }

    @Override
    public ResponseEntity<?> add(CategoryReqDTO dto) {
        return categoryService.add(dto);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CategoryReqDTO dto) {
        return categoryService.edit(id, dto);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return categoryService.delete(id);
    }
}
