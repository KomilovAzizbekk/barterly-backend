package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;

public interface CharacteristicValueService {

    ResponseEntity<Page<?>> getAllByCharacteristicId(Long characteristicId, String language, int page, int size);

    ResponseEntity<CharacteristicValueResDTO> getById(Long id);

    ResponseEntity<?> add(CharacteristicValueReqDTO dto);

    ResponseEntity<?> edit(Long id, CharacteristicValueReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
