package uz.mediasolutions.barterlybackend.controller.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.barterlybackend.controller.user.abs.FavoriteController;
import uz.mediasolutions.barterlybackend.service.user.abs.FavoriteService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FavoriteControllerImpl implements FavoriteController {

    private final FavoriteService favoriteService;

    @Override
    public ResponseEntity<Page<?>> getByUserId(String lang, int page, int size, HttpSession session, HttpServletRequest request) {
        return favoriteService.getByUserId(lang, page, size, session, request);
    }

    @Override
    public ResponseEntity<?> addFavorite(UUID itemId, boolean like, HttpSession session, HttpServletRequest request) {
        return favoriteService.addFavorite(itemId, like, session, request);
    }
}
