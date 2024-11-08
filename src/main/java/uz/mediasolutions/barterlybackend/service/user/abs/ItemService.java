package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.ItemResDTO;

import java.util.UUID;

public interface ItemService {

    String add(ItemReqDTO dto);

    Page<ItemDTO> getItems(String lang, int page, int size, Boolean premium);

    ItemResDTO getById(String lang, UUID itemId);

    String edit(UUID itemId, ItemEditReqDTO dto);

    Page<ItemDTO> getItemsByUserId(String lang, UUID userId, int page, int size);

    Page<ItemDTO> getMyItems(String lang, int page, int size);
}
