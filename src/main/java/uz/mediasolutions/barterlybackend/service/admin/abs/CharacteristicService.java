package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;

public interface CharacteristicService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long categoryId, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(CharacteristicReqDTO dto);

    ResponseEntity<?> edit(Long id, CharacteristicReqDTO dto);

    ResponseEntity<?> delete(Long id);
}
