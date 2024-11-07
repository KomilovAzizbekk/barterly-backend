package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.ItemController;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.ItemResDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ItemControllerImpl implements ItemController {

    private final ItemService itemService;

    @Override
    public ResponseEntity<String> addItem(ItemReqDTO dto) {
        return ResponseEntity.status(201).body(itemService.add(dto));
    }

    @Override
    public ResponseEntity<ItemResDTO> getItemById(String lang, UUID itemId) {
        return ResponseEntity.ok(itemService.getById(lang, itemId));
    }

    @Override
    public ResponseEntity<String> edit(UUID itemId, ItemEditReqDTO dto) {
        return ResponseEntity.status(202).body(itemService.edit(itemId, dto));
    }
}
