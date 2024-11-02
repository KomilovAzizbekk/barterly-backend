package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryCharacteristicResDTO;

public interface CategoryCharacteristicMapper {

    CategoryCharacteristic toEntity(CategoryCharacteristicReqDTO dto);

    CategoryCharacteristicResDTO toDTO(CategoryCharacteristic entity, Category category, CategoryCharacteristic parent, String lang);
}
