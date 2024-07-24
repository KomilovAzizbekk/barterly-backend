package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;

public interface CategoryCharacteristicValueMapper {

    CharacteristicValueResDTO toResDTO(CategoryCharacteristicValue categoryCharacteristicValue);

    CategoryCharacteristicValue toEntity(CategoryCharacteristicValueReqDTO dto);
}
