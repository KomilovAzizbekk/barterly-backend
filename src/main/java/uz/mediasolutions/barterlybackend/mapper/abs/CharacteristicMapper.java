package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;

public interface CharacteristicMapper {

    CharacteristicResDTO toResDTO(Characteristic characteristic);

    Characteristic toEntity(CharacteristicReqDTO dto);

}
