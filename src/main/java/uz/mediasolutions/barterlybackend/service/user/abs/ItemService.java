package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.http.ResponseEntity;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;

import java.util.UUID;

public interface ItemService {

    ResponseEntity<?> add(ItemReqDTO dto);

    ResponseEntity<?> getById(String lang, UUID itemId, boolean active);

    ResponseEntity<?> edit(UUID itemId, ItemEditReqDTO dto);

}
