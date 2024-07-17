package uz.mediasolutions.barterlybackend.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.CurrencyReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CurrencyResDTO;

public interface CurrencyService {

    ResponseEntity<Page<?>> getAll(String language, int page, int size);

    ResponseEntity<CurrencyResDTO> getById(Long id);

    ResponseEntity<?> add(CurrencyReqDTO dto);

    ResponseEntity<?> edit(Long id, CurrencyReqDTO dto);

    ResponseEntity<?> delete(Long id);
}
