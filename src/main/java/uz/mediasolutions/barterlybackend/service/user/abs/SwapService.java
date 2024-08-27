package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.SwapReqDTO;

import java.util.UUID;

public interface SwapService {

    ResponseEntity<?> getAll(String lang, UUID userId, int page, int size);

    ResponseEntity<?> create(SwapReqDTO dto);

    ResponseEntity<?> accept(UUID swapId, boolean accept);
}
