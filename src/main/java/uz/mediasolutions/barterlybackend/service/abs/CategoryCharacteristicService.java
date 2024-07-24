package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;

public interface CategoryCharacteristicService {

    ResponseEntity<Page<?>> getAll(String language, int page, int size);

    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> add(CategoryCharacteristicReqDTO dto);

    ResponseEntity<?> edit(Long id, CategoryCharacteristicReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
