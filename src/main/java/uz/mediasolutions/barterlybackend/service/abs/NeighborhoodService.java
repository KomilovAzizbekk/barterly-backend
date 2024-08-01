package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.NeighborhoodReqDTO;

public interface NeighborhoodService {

    ResponseEntity<Page<?>> getAll(String lang, String search, Long regionId, Long cityId, int page, int size);

    ResponseEntity<?> getById(String lang, Long id);

    ResponseEntity<?> add(NeighborhoodReqDTO dto);

    ResponseEntity<?> edit(Long id, NeighborhoodReqDTO dto);

    ResponseEntity<?> delete(Long id);

}
