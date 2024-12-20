package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryMapper;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {

    private final CategoryRepository categoryRepository;

    @Override
    public Category toEntity(CategoryReqDTO dto) {
        if (dto == null) {
            return null;
        }

        Category parentCategory = Optional.ofNullable(dto.getParentCategoryId())
                .flatMap(categoryRepository::findById)
                .orElse(null);

        return Category.builder()
                .translations(dto.getTranslations())
                .parentCategory(parentCategory)
                .imageUrl(dto.getImageUrl())
                .build();
    }

    @Override
    public CategoryResDTO toResDTO(Category category, Category parentCategory, String lang) {
        if (category == null) {
            return null;
        }

        return CategoryResDTO.builder()
                .id(category.getId())
                .imageUrl(category.getImageUrl())
                .names(category.getTranslations())
                .parentId(parentCategory != null ? parentCategory.getId() : null)
                .parentName(parentCategory != null ? parentCategory.getTranslations().get(lang) : null)
                .build();
    }
}
