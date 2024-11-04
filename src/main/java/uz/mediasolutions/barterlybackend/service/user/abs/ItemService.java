package uz.mediasolutions.barterlybackend.service.user.abs;

import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.Item2DTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;

import java.util.UUID;

public interface ItemService {

    String add(ItemReqDTO dto);

    Item2DTO getById(String lang, UUID itemId, boolean active);

    String edit(UUID itemId, ItemEditReqDTO dto);

}
