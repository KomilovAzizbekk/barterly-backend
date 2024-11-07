package uz.mediasolutions.barterlybackend.service.user.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.response.HeaderResDTO;

public interface HomeService {

    HeaderResDTO getHeaderDetails();

    Page<ItemDTO> getItems(String lang, int page, int size, Boolean premium);

    Page<ItemDTO> search(String search, Long categoryId);
}
