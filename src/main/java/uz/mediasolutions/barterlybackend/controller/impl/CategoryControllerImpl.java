package uz.mediasolutions.barterlybackend.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.abs.CategoryController;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;
import uz.mediasolutions.barterlybackend.service.abs.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Page<?>> getAllParents(String language, int page, int size) {
        return categoryService.getAllParents(language, page, size);
    }

    @Override
    public ResponseEntity<Page<?>> getAllByParentId(Long parentId, String language, int page, int size) {
        return categoryService.getAllByParentId(parentId, language, page, size);
    }

    @Override
    public ResponseEntity<CategoryResDTO> getById(Long id) {
        return categoryService.getById(id);
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
