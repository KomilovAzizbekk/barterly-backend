package uz.mediasolutions.barterlybackend.service.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.repository.FavoriteRepository;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.repository.SwapRepository;
import uz.mediasolutions.barterlybackend.service.common.impl.AuthServiceImpl;
import uz.mediasolutions.barterlybackend.service.user.abs.HomeService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final AuthServiceImpl authService;
    private final SwapRepository swapRepository;
    private final FavoriteRepository favoriteRepository;
    private final ItemRepository itemRepository;

    @Override
    public ResponseEntity<HeaderResDTO> getHeaderDetails(HttpServletRequest request, HttpSession session) {
        UUID userId = authService.getAuthenticatedUserId(request);
        int favourite = 0, swap, profile;
        if (userId == null) {
            swap = 0;
            profile = 0;
            Set<UUID> likedItems = (Set<UUID>) session.getAttribute("likedItems");
            if (likedItems == null) {
                likedItems = new HashSet<>();
            }
            favourite = likedItems.size();
        } else {
            swap = swapRepository.findCountByUserId(userId);
            profile = 0;
            favourite = favoriteRepository.findCountByUserId(userId);
        }
        HeaderResDTO headerResDTO = HeaderResDTO.builder()
                .profile(profile)
                .swaps(swap)
                .favorites(favourite)
                .build();
        return ResponseEntity.ok(headerResDTO);
    }

    @Override
    public ResponseEntity<Page<?>> getItems(String lang, int page, int size, HttpServletRequest request, HttpSession session) {
        Pageable pageable = PageRequest.of(page, size);
        UUID userId = authService.getAuthenticatedUserId(request);
        Page<ItemDTO> items;
        if (userId == null) {
            Set<UUID> likedItems = (Set<UUID>) session.getAttribute("likedItems");
            if (likedItems == null) {
               likedItems = new HashSet<>();
            }
            items = itemRepository.findAllForHomeForNotAuthenticatedUser(lang, new ArrayList<>(likedItems), pageable);
        } else {
            items = itemRepository.findAllForHomeForAuthenticatedUser(lang, userId, pageable);
        }
        return ResponseEntity.ok(items);
    }

    @Override
    public ResponseEntity<Page<?>> search(String search, Long categoryId) {
        return null;
    }
}
