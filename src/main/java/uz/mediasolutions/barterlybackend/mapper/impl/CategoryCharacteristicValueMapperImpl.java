package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristicValue;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryCharacteristicValueMapper;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicRepository;

@Component
@RequiredArgsConstructor
public class CategoryCharacteristicValueMapperImpl implements CategoryCharacteristicValueMapper {

    private final CategoryCharacteristicRepository characteristicRepository;

    @Override
    public CharacteristicValueResDTO toResDTO(CategoryCharacteristicValue categoryCharacteristicValue) {
        if (categoryCharacteristicValue == null) {
            return null;
        }

        return CharacteristicValueResDTO.builder()
                .id(categoryCharacteristicValue.getId())
                .translations(categoryCharacteristicValue.getTranslations())
                .build();
    }

    @Override
    public CategoryCharacteristicValue toEntity(CategoryCharacteristicValueReqDTO dto) {
        if (dto == null) {
            return null;
        }

        CategoryCharacteristic categoryCharacteristic = characteristicRepository.findById(dto.getCategoryCharacteristicId()).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.BAD_REQUEST)
        );

        return CategoryCharacteristicValue.builder()
                .translations(dto.getTranslations())
                .characteristic(categoryCharacteristic)
                .build();
    }
}
