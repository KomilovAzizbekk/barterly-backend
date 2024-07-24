package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryCharacteristicResDTO;

public interface CategoryCharacteristicMapper {

    CategoryCharacteristicResDTO toResDTO(CategoryCharacteristic categoryCharacteristic);

    CategoryCharacteristic toEntity(CategoryCharacteristicReqDTO dto);
}
