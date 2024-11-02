package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryCharacteristicValueResDTO;

public interface CategoryCharacteristicValueMapper {

    CategoryCharacteristicValue toEntity(CategoryCharacteristicValueReqDTO dto);

    CategoryCharacteristicValueResDTO toResDTO(CategoryCharacteristicValue categoryCharacteristicValue,
                                                CategoryCharacteristic categoryCharacteristic, String lang);
}
