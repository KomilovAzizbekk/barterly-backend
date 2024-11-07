package uz.mediasolutions.barterlybackend.service.user.abs;

import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.ItemResDTO;

import java.util.UUID;

public interface ItemService {

    String add(ItemReqDTO dto);

    ItemResDTO getById(String lang, UUID itemId);

    String edit(UUID itemId, ItemEditReqDTO dto);

}
