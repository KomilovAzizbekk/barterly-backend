package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;

public interface CategoryService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long parentId, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(CategoryReqDTO dto);

    ResponseEntity<?> edit(Long id, CategoryReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
