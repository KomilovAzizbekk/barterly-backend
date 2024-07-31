package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicRepository;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;

@Component
@AllArgsConstructor
public class CharacteristicMapperImpl implements CharacteristicMapper {

    private final CategoryRepository categoryRepository;
    private final CategoryCharacteristicRepository categoryCharacteristicRepository;

    @Override
    public CharacteristicResDTO toResDTO(Characteristic characteristic) {
        if (characteristic == null) {
            return null;
        }

        return CharacteristicResDTO.builder()
                .id(characteristic.getId())
                .required(characteristic.isRequired())
                .translations(characteristic.getTranslations())
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

        return Characteristic.builder()
                .required(dto.isRequired())
                .translations(dto.getTranslations())
                .category(category)
                .build();
    }
}
