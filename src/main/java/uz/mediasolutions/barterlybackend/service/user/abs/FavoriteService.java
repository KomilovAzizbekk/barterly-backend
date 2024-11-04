package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.entity.Favorite;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;

import java.util.UUID;

public interface FavoriteService {

    Page<ItemDTO> getByUserId(String lang, int page, int size);

    Favorite addFavorite(UUID itemId, boolean like);

}
