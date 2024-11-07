package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.entity.CharacteristicType;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.repository.CharacteristicTypeRepository;

@Component
@AllArgsConstructor
public class CharacteristicMapperImpl implements CharacteristicMapper {

    private final CategoryRepository categoryRepository;
    private final CharacteristicTypeRepository characteristicTypeRepository;

    @Override
    public CharacteristicResDTO toResDTO(Characteristic characteristic, String lang) {
        if (characteristic == null) {
            return null;
        }

        // Characteristic'ga bog'langan Categoryni olamiz
        Category category = characteristic.getCategory();

        return CharacteristicResDTO.builder()
                .id(characteristic.getId())
                .filter(characteristic.isFilter())
                .title(characteristic.isTitle())
                .names(characteristic.getTranslations())
                .categoryId(category.getId())
                .categoryName(category.getTranslations().get(lang))
                .characteristicTypeId(characteristic.getCharacteristicType().getId())
                .characteristicTypeName(characteristic.getCharacteristicType().getTranslations().get(lang))
                .build();
    }

    @Override
    public Characteristic toEntity(CharacteristicReqDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        );

        CharacteristicType characteristicType = characteristicTypeRepository.findById(dto.getCharacteristicTypeId()).orElseThrow(
                () -> RestException.restThrow("Characteristic type not found", HttpStatus.BAD_REQUEST)
        );

        return Characteristic.builder()
                .title(dto.getTitle())
                .filter(dto.getFilter())
                .translations(dto.getTranslations())
                .category(category)
                .characteristicType(characteristicType)
                .build();
    }
}
