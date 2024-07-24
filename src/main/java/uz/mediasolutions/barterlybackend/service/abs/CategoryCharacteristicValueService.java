package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicValueResDTO;

public interface CategoryCharacteristicValueService {

    ResponseEntity<Page<?>> getAllByCategoryCharacteristicId(Long categoryCharacteristicId, String language, int page, int size);

    ResponseEntity<CharacteristicValueResDTO> getById(Long id);

    ResponseEntity<?> add(CategoryCharacteristicValueReqDTO dto);

    ResponseEntity<?> edit(Long id, CategoryCharacteristicValueReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
