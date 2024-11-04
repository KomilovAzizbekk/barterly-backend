package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.CatalogDTO;
import uz.mediasolutions.barterlybackend.payload.response.CatalogResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.CatalogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CatalogDTO> getParents(String lang) {
        return categoryRepository.findAllParentCatalogs(lang);
    }

    @Override
    public List<CatalogDTO> getByParentId(String lang, Long parentId) {
        return categoryRepository.findAllByParentId(lang, parentId);
    }

    @Override
    public List<CatalogResDTO> getAllByParentId(String lang, Long parentId) {
        // Barcha kategoriyalarni get qilib olamiz
        List<Category> allCategories = categoryRepository.findAll();

        // ParentID ga bog'liq ravishda kategoriyalarni Map'da saqlaymiz
        Map<Long, List<Category>> categoryMap = allCategories.stream()
                .collect(Collectors.groupingBy(category -> category.getParentCategory() == null ? 0L : category.getParentCategory().getId()));

        // Rekursiyasiz CatalogResDTO'ni Listini yaratib olamiz va qaytaramiz
        return buildCategoryTree(categoryMap, parentId, lang);
    }

    private List<CatalogResDTO> buildCategoryTree(Map<Long, List<Category>> categoryMap, Long parentId, String lang) {
        List<Category> categories = categoryMap.getOrDefault(parentId, new ArrayList<>());

        return categories.stream().map(category -> {
            // Sub-kategoriyalarni rekursiyasiz chaqiramiz
            List<CatalogResDTO> childCategories = buildCategoryTree(categoryMap, category.getId(), lang);

            // DTO yaratamiz
            return CatalogResDTO.builder()
                    .id(category.getId())
                    .name(category.getTranslations().get(lang))
                    .catalog(childCategories.isEmpty() ? null : childCategories)
                    .build();
        }).collect(Collectors.toList());
    }

}
