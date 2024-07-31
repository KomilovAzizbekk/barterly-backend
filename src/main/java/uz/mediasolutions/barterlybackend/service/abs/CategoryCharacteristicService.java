package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;

public interface CategoryCharacteristicService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long categoryId,
                                   Long parentCharacteristicId, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(CategoryCharacteristicReqDTO dto);

    ResponseEntity<?> edit(Long id, CategoryCharacteristicReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
