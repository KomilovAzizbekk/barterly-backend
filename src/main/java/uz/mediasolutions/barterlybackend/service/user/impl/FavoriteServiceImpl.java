package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Favorite;
import uz.mediasolutions.barterlybackend.entity.Item;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.repository.FavoriteRepository;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.FavoriteService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ItemRepository itemRepository;

    @Override
    public Page<ItemDTO> getByUserId(String lang, int page, int size) {
        // Security contextdan user'ni get qilib olamiz
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Agar user contextda topilmasa 401 qaytarib yuboramiz
        if (user == null) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return favoriteRepository.findAllByUserId(user.getId(), lang, PageRequest.of(page, size));
    }

    @Override
    public Favorite addFavorite(UUID itemId, boolean like) {
        // Security Context'dan userni olamiz
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Agar User null bo'lsa 401 xatolik qayaramiz
        if (user == null) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Item'ni bazadan izlaymiz, agar yo'q bo'lsa 404 qaytaramiz
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> RestException.restThrow("Item not found", HttpStatus.NOT_FOUND)
        );

        /* Agar like = true && bazada duplikatlikka olib kelmasa bazaga yangi Favorite object qo'shamiz,
        else if like = false && bazada shunday object bo'lsa holda delete qilib yuboramiz
        else BAD REQUEST */
        if (like && !favoriteRepository.existsByUserIdAndItemId(user.getId(), itemId)) {
            return favoriteRepository.save(
                    Favorite.builder()
                            .user(user)
                            .item(item)
                            .build());
        } else if (!like && favoriteRepository.existsByUserIdAndItemId(user.getId(), itemId)) {
            favoriteRepository.deleteFavoritesByUserIdAndItemIdCustom(user.getId(), itemId);
            return null;
        } else {
            throw RestException.restThrow("Wrong action", HttpStatus.BAD_REQUEST);
        }
    }
}
