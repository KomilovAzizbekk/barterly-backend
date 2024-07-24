package uz.mediasolutions.barterlybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryCharacteristicResDTO;

@Mapper(componentModel = "spring")
public interface CategoryCharacteristicMapper {

    CategoryCharacteristicResDTO toResDTO(CategoryCharacteristic categoryCharacteristic);

    @Mapping(target = "parent.id", source = "parentId")
    @Mapping(target = "category.id", source = "categoryId")
    CategoryCharacteristic toEntity(CategoryCharacteristicReqDTO dto);

}
