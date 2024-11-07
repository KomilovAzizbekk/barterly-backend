package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.CharacteristicType;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicTypeMapper;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicTypeReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;

@Component
@AllArgsConstructor
public class CharacteristicTypeMapperImpl implements CharacteristicTypeMapper {

    private final CategoryRepository categoryRepository;

    @Override
    public CharacteristicType toEntity(CharacteristicTypeReqDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        return CharacteristicType.builder()
                .translations(dto.getTranslations())
                .category(category)
                .build();
    }

    @Override
    public CharacteristicTypeResDTO toResDTO(CharacteristicType entity, String lang) {
        if (entity == null) {
            return null;
        }

        return CharacteristicTypeResDTO.builder()
                .id(entity.getId())
                .names(entity.getTranslations())
                .categoryName(entity.getCategory().getTranslations().get(lang))
                .categoryId(entity.getCategory().getId())
                .build();
    }
}
