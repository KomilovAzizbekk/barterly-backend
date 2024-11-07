package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.CharacteristicType;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicTypeReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO;

public interface CharacteristicTypeMapper {

    CharacteristicType toEntity(CharacteristicTypeReqDTO dto);

    CharacteristicTypeResDTO toResDTO(CharacteristicType entity, String lang);
}
