package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CityReqDTO;

public interface CityService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(CityReqDTO dto);

    ResponseEntity<?> edit(CityReqDTO dto, Long id);

    ResponseEntity<?> delete(Long id);
}
