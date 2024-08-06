package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;

public interface CategoryCharacteristicValueService {

    ResponseEntity<Page<?>> getAll(String lang, Long categoryCharacteristicId, String search, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(CategoryCharacteristicValueReqDTO dto);

    ResponseEntity<?> edit(Long id, CategoryCharacteristicValueReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
