package uz.mediasolutions.barterlybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;

@Mapper(componentModel = "spring")
public interface CharacteristicMapper {

    CharacteristicResDTO toResDTO(Characteristic characteristic);

    @Mapping(target = "category.id", source = "categoryId")
    Characteristic toEntity(CharacteristicReqDTO dto);

}
