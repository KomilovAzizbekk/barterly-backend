package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;
import uz.mediasolutions.barterlybackend.repository.FavoriteRepository;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.repository.SwapRepository;
import uz.mediasolutions.barterlybackend.service.user.abs.HomeService;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final SwapRepository swapRepository;
    private final FavoriteRepository favoriteRepository;
    private final ItemRepository itemRepository;

    @Override
    public HeaderResDTO getHeaderDetails() {
        // Security contextdan user'ni get qilib olamiz
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Agar user contextda topilmasa 401 qaytarib yuboramiz
        if (user == null) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // 3 ta o'zgaruvchi yaratib olamiz
        int swap = swapRepository.findCountByUserId(user.getId());
        int profile = 0;
        int favourite = favoriteRepository.findCountByUserId(user.getId());

        return HeaderResDTO.builder()
                .profile(profile)
                .swaps(swap)
                .favorites(favourite)
                .build();
    }

    @Override
    public Page<ItemDTO> search(String search, Long categoryId) {
        return null;
    }
}
