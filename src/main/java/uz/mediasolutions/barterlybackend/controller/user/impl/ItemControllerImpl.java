package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.ItemController;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ItemControllerImpl implements ItemController {

    private final ItemService itemService;

    @Override
    public ResponseEntity<?> addItem(ItemReqDTO dto) {
        return itemService.add(dto);
    }

    @Override
    public ResponseEntity<?> getItemById(String lang, UUID itemId) {
        return itemService.getById(lang, itemId);
    }
}
