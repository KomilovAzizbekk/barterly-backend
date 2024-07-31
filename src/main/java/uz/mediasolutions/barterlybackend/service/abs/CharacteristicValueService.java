package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;

public interface CharacteristicValueService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long characteristicId, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(CharacteristicValueReqDTO dto);

    ResponseEntity<?> edit(Long id, CharacteristicValueReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
