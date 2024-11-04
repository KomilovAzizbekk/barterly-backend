package uz.mediasolutions.barterlybackend.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.FavoriteController;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.service.user.abs.FavoriteService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FavoriteControllerImpl implements FavoriteController {

    private final FavoriteService favoriteService;

    @Override
    public ResponseEntity<Page<ItemDTO>> getByUserId(String lang, int page, int size) {
        return ResponseEntity.ok(favoriteService.getByUserId(lang, page, size));
    }

    @Override
    public ResponseEntity<String> addFavorite(UUID itemId, boolean like) {
        return ResponseEntity.status(201).body(favoriteService.addFavorite(itemId, like));
    }
}
