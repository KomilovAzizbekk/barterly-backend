package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.entity.CharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;

public interface CharacteristicValueMapper {

    CharacteristicValueResDTO toResDTO(CharacteristicValue characteristicValue, Characteristic characteristic, String lang);

    CharacteristicValue toEntity(CharacteristicValueReqDTO value);

}
