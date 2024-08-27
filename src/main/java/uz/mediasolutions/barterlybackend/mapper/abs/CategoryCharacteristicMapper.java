package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;

public interface CategoryCharacteristicMapper {

    CategoryCharacteristic toEntity(CategoryCharacteristicReqDTO dto);
}
