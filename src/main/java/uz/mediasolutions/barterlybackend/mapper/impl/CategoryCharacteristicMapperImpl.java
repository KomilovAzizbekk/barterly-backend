package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryCharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryCharacteristicResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicRepository;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;

@Component
@RequiredArgsConstructor
public class CategoryCharacteristicMapperImpl implements CategoryCharacteristicMapper {

    private final CategoryCharacteristicRepository categoryCharacteristicRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryCharacteristicResDTO toResDTO(CategoryCharacteristic categoryCharacteristic) {
        if (categoryCharacteristic == null) {
            return null;
        }

        return CategoryCharacteristicResDTO
                .builder()
                .id(categoryCharacteristic.getId())
                .translations(categoryCharacteristic.getTranslations())
                .build();
    }

    @Override
    public CategoryCharacteristic toEntity(CategoryCharacteristicReqDTO dto) {
        if (dto == null) {
            return null;
        }
        CategoryCharacteristic categoryCharacteristic = new CategoryCharacteristic();

        if (dto.getParentId() != null) {
            categoryCharacteristic.setParent(categoryCharacteristicRepository.findById(dto.getParentId()).orElse(null));
        }

        if (dto.getCategoryId() == null) {
            throw RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST);
        }

        categoryCharacteristic.setCategory(categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        ));
        categoryCharacteristic.setTranslations(dto.getTranslations());
        return categoryCharacteristic;
    }
}
