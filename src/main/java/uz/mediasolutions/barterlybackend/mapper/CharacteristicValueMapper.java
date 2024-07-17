package uz.mediasolutions.barterlybackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.barterlybackend.entity.CharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;

@Mapper(componentModel = "spring")
public interface CharacteristicValueMapper {

    CharacteristicValueResDTO toResDTO(CharacteristicValue value);

    @Mapping(target = "characteristic.id", source = "characteristicId")
    CharacteristicValue toEntity(CharacteristicValueReqDTO value);

}
