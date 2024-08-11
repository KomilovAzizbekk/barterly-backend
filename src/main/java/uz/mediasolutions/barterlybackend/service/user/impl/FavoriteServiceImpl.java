package uz.mediasolutions.barterlybackend.service.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Favorite;
import uz.mediasolutions.barterlybackend.entity.Item;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.repository.FavoriteRepository;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.repository.UserRepository;
import uz.mediasolutions.barterlybackend.service.common.impl.AuthServiceImpl;
import uz.mediasolutions.barterlybackend.service.user.abs.FavoriteService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AuthServiceImpl authService;
    private final ItemRepository itemRepository;

    @Override
    public ResponseEntity<Page<?>> getByUserId(String lang, int page, int size, HttpSession session, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        UUID userId = authService.getAuthenticatedUserId(request);
        Page<ItemDTO> favorites;
        if (userId == null) {
            Set<UUID> likedItems = (Set<UUID>) session.getAttribute("likedItems");
            if (likedItems == null) {
                likedItems = new HashSet<>();
            }
            favorites = favoriteRepository.findAllByItemIds(new ArrayList<>(likedItems), lang, pageable);
        } else {
            favorites = favoriteRepository.findAllByUserId(userId, lang, pageable);
        }
        return ResponseEntity.ok(favorites);
    }

    @Override
    public ResponseEntity<?> addFavorite(UUID itemId, boolean like, HttpSession session, HttpServletRequest request) {
        UUID userId = authService.getAuthenticatedUserId(request);
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> RestException.restThrow("Item not found", HttpStatus.NOT_FOUND)
        );

        if (userId == null) {
            Set<UUID> likedItems = (Set<UUID>) session.getAttribute("likedItems");
            if (likedItems == null) {
                likedItems = new HashSet<>();
            }
            if (like) {
                likedItems.add(itemId);
                session.setAttribute("likedItems", likedItems);
            } else {
                likedItems.remove(itemId);
                session.setAttribute("likedItems", likedItems);
            }
        } else {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> RestException.restThrow("User not found", HttpStatus.NOT_FOUND)
            );

            if (like) {
                Favorite favorite = Favorite.builder()
                        .user(user)
                        .item(item)
                        .build();
                favoriteRepository.save(favorite);
            } else {
                Favorite favorite = favoriteRepository.findByUserIdAndItemId(userId, itemId);
                favoriteRepository.deleteById(favorite.getId());
            }
        }
        return like ? ResponseEntity.ok("LIKED") : ResponseEntity.ok("UNLIKED");
    }
}
