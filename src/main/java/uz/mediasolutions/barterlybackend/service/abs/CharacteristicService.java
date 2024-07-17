package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;

public interface CharacteristicService {

    ResponseEntity<Page<?>> getAll(String language, int page, int size);

    ResponseEntity<CharacteristicResDTO> getById(Long id);

    ResponseEntity<?> add(CharacteristicReqDTO dto);

    ResponseEntity<?> edit(Long id, CharacteristicReqDTO dto);

    ResponseEntity<?> delete(Long id);
}
