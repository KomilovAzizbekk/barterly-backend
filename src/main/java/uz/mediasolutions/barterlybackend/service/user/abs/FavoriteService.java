package uz.mediasolutions.barterlybackend.service.user.abs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface FavoriteService {

    ResponseEntity<Page<?>> getByUserId(String lang, int page, int size, HttpSession session, HttpServletRequest request);

    ResponseEntity<?> addFavorite(UUID itemId, boolean like, HttpSession session, HttpServletRequest request);

}
