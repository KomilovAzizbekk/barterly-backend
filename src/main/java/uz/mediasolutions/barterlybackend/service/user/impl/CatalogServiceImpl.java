package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.CatalogDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.CatalogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<?>> getParents(String lang) {
        List<CatalogDTO> allParentCatalogs = categoryRepository.findAllParentCatalogs(lang);
        return ResponseEntity.ok(allParentCatalogs);
    }

    @Override
    public ResponseEntity<List<?>> getByParentId(String lang, Long parentId) {
        List<CategoryDTO> allByParentId = categoryRepository.findAllByParentId(lang, parentId);
        return ResponseEntity.ok(allByParentId);
    }
}
