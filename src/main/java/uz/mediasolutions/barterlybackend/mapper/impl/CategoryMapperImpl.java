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
    public CategoryResDTO toResDTO(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResDTO.builder()
                .id(category.getId())
                .translations(category.getTranslations())
                .imageUrl(category.getImageUrl())
                .build();
    }

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
}
