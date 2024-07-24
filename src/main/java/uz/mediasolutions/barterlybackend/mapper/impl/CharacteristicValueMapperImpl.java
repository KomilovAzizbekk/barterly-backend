package uz.mediasolutions.barterlybackend.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.entity.CharacteristicValue;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicValueMapper;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;
import uz.mediasolutions.barterlybackend.repository.CharacteristicRepository;

@Component
@RequiredArgsConstructor
public class CharacteristicValueMapperImpl implements CharacteristicValueMapper {

    private final CharacteristicRepository characteristicRepository;

    @Override
    public CharacteristicValueResDTO toResDTO(CharacteristicValue value) {
        if (value == null) {
            return null;
        }

        return CharacteristicValueResDTO.builder()
                .id(value.getId())
                .translations(value.getTranslations())
                .build();
    }

    @Override
    public CharacteristicValue toEntity(CharacteristicValueReqDTO value) {
        if (value == null) {
            return null;
        }

        Characteristic characteristic = characteristicRepository.findById(value.getCharacteristicId()).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.BAD_REQUEST)
        );

        return CharacteristicValue.builder()
                .translations(value.getTranslations())
                .characteristic(characteristic)
                .build();
    }
}
