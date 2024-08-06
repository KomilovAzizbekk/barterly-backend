package uz.mediasolutions.barterlybackend.service.admin.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.RegionReqDTO;

public interface RegionService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long currencyId, int page, int size);

    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> add(RegionReqDTO dto);

    ResponseEntity<?> edit(Long id, RegionReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
